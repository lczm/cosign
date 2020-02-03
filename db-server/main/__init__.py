from flask import Flask
app = Flask(__name__)
app.config['SECRET_KEY'] = 'dev'

from flask_sqlalchemy import SQLAlchemy
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///site.db'
db = SQLAlchemy(app)

from flask_bcrypt import Bcrypt
bcrypt = Bcrypt(app)

import wtforms_json
wtforms_json.init()

from main import routes
