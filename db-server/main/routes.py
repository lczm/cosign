from flask import request, jsonify
from main import app, db, bcrypt
from main.models import User, Sign, Category, Learn, Bookmark, Goal
from main.forms import RegisterForm, LoginForm, AuthForm, LearnForm, BookmarkForm, GoalForm
from functools import wraps

def validate_form(form_type):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            form = form_type.from_json(request.json)
            if form.validate():
                return func(form, *args, **kwargs)
            return jsonify(form.errors), 400
        return wrapper
    return decorator

def login_required(func):
    @wraps(func)
    @validate_form(AuthForm)
    def wrapper(form, *args, **kwargs):
        user = User.query.filter_by(email=form.email.data).first()
        if user and bcrypt.check_password_hash(user.password, form.password.data):
            return func(user, *args, **kwargs)
        return jsonify({'error': 'Invalid Email or Password'}), 400
    return wrapper

@app.route('/register', methods=['POST'])
@validate_form(RegisterForm)
def register(form):
    hashed_password = bcrypt.generate_password_hash(form.password.data).decode('utf-8')
    user = User(
        email=form.email.data, 
        password=hashed_password,
        username=form.username.data)
    db.session.add(user)
    db.session.commit()
    return '', 200

@app.route('/login', methods=['POST'])
@validate_form(LoginForm)
def login(form):
    user = User.query.filter_by(email=form.email.data).first()
    if user and bcrypt.check_password_hash(user.password, form.password.data):
        return '', 200
    return jsonify({'error': 'Invalid Email or Password'}), 400

@app.route('/learn', methods=['POST'])
@login_required
@validate_form(LearnForm)
def learn(form, user):
    sign_id = form.sign_id.data
    sign = Sign.query.get(sign_id)
    if not sign:
        return jsonify({'error': 'Handsign does not exist'}), 400
    user_id = user.user_id
    learn = Learn.query.get((user_id, sign_id))
    if learn:
        return jsonify({'error': 'Handsign already learnt'}), 404
    learn = Learn(user_id=user_id, sign_id=sign_id)
    db.session.add(learn)
    db.session.commit()
    return '', 200

@app.route('/goal', methods=['POST'])
@login_required
@validate_form(GoalForm)
def goal(form, user):
    goal_id = form.goal_id.data
    if goal_id == -1:
        goal = Goal(
            date=form.date.data, 
            amount=form.amount.data)
        db.session.add(goal)
    else:
        goal = Goal.query.get(goal_id)
        if not goal:
            jsonify({'error': 'Goal does not exist'}), 400
        goal.date = form.date.data
        goal.amount = form.amount.data
    db.session.commit()
    return '', 200

@app.route('/bookmark', methods=['POST'])
@login_required
@validate_form(BookmarkForm)
def bookmark(form, user):
    sign_id = form.sign_id.data
    sign = Sign.query.get(sign_id)
    if not sign:
        return jsonify({'error': 'Handsign does not exist'}), 400
    user_id = user.user_id
    bookmark = Bookmark.query.get((user_id, sign_id))
    if bookmark:
        db.session.delete(bookmark)
    else:
        bookmark = Bookmark(user_id=user_id, sign_id=sign_id)
        db.session.add(bookmark)
    db.session.commit()
    return '', 200

@app.route('/handsigns', methods=['GET'])
def handsigns():
    signs, categories = {}, {}
    for category in Category.query.all():
        catSigns = []
        for sign in category.signs:
            catSigns.append(sign.sign_id)
            signs[sign.sign_id] = {
                'name': sign.name
            }
        categories[category.cat_id] = {
            'name': category.name,
            'sign_ids': catSigns
        }
    return jsonify({'signs': signs, 'categories': categories})

@app.route('/profile', methods=['POST'])
@login_required
def profile(user):
    learns = {sign.sign_id: {'date': sign.date} for sign in user.learns}
    bookmarks = {bookmark.sign_id: {} for bookmark in user.bookmarks}
    goals = {goal.goal_id: {'date': goal.date} for goal in user.goals}
    return jsonify({'learns': learns, 'bookmarks': bookmarks, 'goals': goals})
