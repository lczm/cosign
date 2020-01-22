#pylint: skip-file
from main.models import db, User, Sign, Category, Learn, Bookmark

Learn.query.delete()
Bookmark.query.delete()
Sign.query.delete()
Category.query.delete()
db.session.commit()

num_cat = Category(name='Numbers')
abc_cat = Category(name='Alphabets')
db.session.add(num_cat)
db.session.add(abc_cat)
db.session.commit()

db.session.add(Sign(name='One', cat_id=num_cat.cat_id))
db.session.add(Sign(name='Two', cat_id=num_cat.cat_id))
db.session.add(Sign(name='Three', cat_id=num_cat.cat_id))
db.session.add(Sign(name='A', cat_id=abc_cat.cat_id))
db.session.add(Sign(name='B', cat_id=abc_cat.cat_id))
db.session.add(Sign(name='C', cat_id=abc_cat.cat_id))
db.session.commit()