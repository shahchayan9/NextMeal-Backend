from app import app, db

# Create tables within the application context
with app.app_context():
    db.create_all()