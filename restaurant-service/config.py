import os

class Config:
    # PostgreSQL configuration
    SQLALCHEMY_DATABASE_URI = os.getenv('DB_URL')  # Default value for local dev
    SQLALCHEMY_TRACK_MODIFICATIONS = False

    # Redis configuration (default for local dev)
    REDIS_URL = os.getenv('REDIS_URL', "redis://localhost:6379/0")

    # Secret key for sessions or cookies (you can change this)
    SECRET_KEY = os.urandom(24)