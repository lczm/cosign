from wtforms import Form
from wtforms.fields import TextField
from wtforms.validators import ValidationError, InputRequired, Length
from main.models import User

class RegisterForm(Form):
    email       = TextField('email',    validators=[InputRequired(), Length(max=50)])
    password    = TextField('password', validators=[InputRequired(), Length(min=5, max=72)])

    def validate_email(self, email):
        user = User.query.filter_by(email=email.data).first()
        if user: raise ValidationError('Email is taken. Please use another one.')