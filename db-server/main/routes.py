from flask import request, jsonify
from main import app, db, bcrypt
from main.models import User
from main.forms import RegisterForm

@app.route('/register', methods=['GET','POST'])
def register():
    form = RegisterForm.from_json(request.json)
    if form.validate():
        hashed_password = bcrypt.generate_password_hash(form.password.data).decode('utf-8')
        user = User(
            email=form.email.data, 
            password=hashed_password)
        db.session.add(user)
        db.session.commit()
        return '', 200
    return jsonify(form.errors), 400
