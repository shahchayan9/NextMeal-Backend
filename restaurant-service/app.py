from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS
from db import db
import os

def create_app():
    # Initialize the Flask app
    app = Flask(__name__)

    # Allow cross-origin requests
    CORS(app)

    # Database setup
    app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv('DB_URL')
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

    # Initialize db with app
    db.init_app(app)

    # Import routes after initializing app and db
    from routes import restaurant_routes

    # Register the blueprint for routes
    app.register_blueprint(restaurant_routes)

    # Add a root route to return a simple message or redirect
    @app.route('/')
    def index():
        return 'Welcome to the Restaurant API! Go to /restaurants to view the restaurants.'

    return app

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True, host='0.0.0.0', port=5001)  # 0.0.0.0 allows external access
