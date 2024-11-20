import os

class Config:
    # PostgreSQL configuration
    SQLALCHEMY_DATABASE_URI = 'postgresql://chayanshah:admin@localhost/nextmeal'
    SQLALCHEMY_TRACK_MODIFICATIONS = False

    # Redis configuration
    REDIS_URL = "redis://localhost:6379/0"

    # Secret key for sessions or cookies (you can change this)
    SECRET_KEY = os.urandom(24)