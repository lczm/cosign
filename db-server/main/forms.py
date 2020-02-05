from wtforms import Form
from wtforms.fields import StringField, IntegerField, DateField
from wtforms.validators import ValidationError, InputRequired, Length, Email
from main.models import User

class RegisterForm(Form):
    email       = StringField('email',      validators=[InputRequired(), Length(max=50)])
    password    = StringField('password',   validators=[InputRequired(), Length(min=6, max=72)])
    username    = StringField('username',   validators=[InputRequired(), Length(min=6, max=30)])

    def validate_email(self, email):
        user = User.query.filter_by(email=email.data).first()
        if user: raise ValidationError('Email is taken. Please use another one.')

class LoginForm(Form):
    email       = StringField('email',      validators=[InputRequired(), Length(max=50)])
    password    = StringField('password',   validators=[InputRequired(), Length(min=6, max=72)])

class AuthForm(Form):
    email       = StringField('email',      validators=[InputRequired(), Length(max=50)])
    password    = StringField('password',   validators=[InputRequired(), Length(min=6, max=72)])

class LearnForm(Form):
    sign_id     = IntegerField('sign_id',   validators=[InputRequired()])

class BookmarkForm(Form):
    sign_id     = IntegerField('sign_id',   validators=[InputRequired()])
    
class GoalForm(Form):
    goal_id     = IntegerField('goal_id',   validators=[InputRequired()])
    date        = DateField('date', format='%d/%m/%Y' validators=[InputRequired()])
    amount      = IntegerField('amount',    validators=[InputRequired()])
