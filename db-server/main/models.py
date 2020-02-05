#pylint: skip-file
from main import db
from datetime import datetime

class User(db.Model):
    user_id     = db.Column(db.Integer,     primary_key=True)
    email       = db.Column(db.String(50),  nullable=False, unique=True)
    password    = db.Column(db.String(60),  nullable=False)
    username    = db.Column(db.String(30),  nullable=False)

    learns      = db.relationship('Learn',  backref='user')
    bookmarks   = db.relationship('Bookmark', backref='user')
    goals       = db.relationship('Goal', backref='user')

class Sign(db.Model):
    sign_id     = db.Column(db.Integer,     primary_key=True)
    cat_id      = db.Column(db.Integer,     db.ForeignKey('category.cat_id'))
    name        = db.Column(db.String(20),  nullable=False)

class Category(db.Model):
    cat_id      = db.Column(db.Integer,     primary_key=True)
    name        = db.Column(db.String(20),  nullable=False)

    signs       = db.relationship('Sign',   backref='category')

class Learn(db.Model):
    user_id     = db.Column(db.Integer, db.ForeignKey('user.user_id'), primary_key=True)
    sign_id     = db.Column(db.Integer, db.ForeignKey('sign.sign_id'), primary_key=True)
    date        = db.Column(db.DateTime,    nullable=False, default=datetime.utcnow())

class Bookmark(db.Model):
    user_id     = db.Column(db.Integer, db.ForeignKey('user.user_id'), primary_key=True)
    sign_id     = db.Column(db.Integer, db.ForeignKey('sign.sign_id'), primary_key=True)

class Goal(db.Model):
    goal_id     = db.Column(db.Integer,     primary_key=True)
    user_id     = db.Column(db.Integer, db.ForeignKey('user.user_id'))
    date        = db.Column(db.Date,        nullable=False)
    amount      = db.Column(db.Integer,     nullable=False)