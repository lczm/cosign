#pylint: skip-file
from main import db

class User(db.Model):
    user_id     = db.Column(db.Integer,     primary_key=True)
    email       = db.Column(db.String(50),  nullable=False, unique=True)
    password    = db.Column(db.String(60),  nullable=False) 