import os
from dotenv import load_dotenv

class Config:
    load_dotenv()
    # PostgreSQL configuration
    SQLALCHEMY_DATABASE_URI = os.getenv('DB_URL')
    SQLALCHEMY_TRACK_MODIFICATIONS = False

    # Redis configuration (default for local dev)
    REDIS_URL = os.getenv('REDIS_URL', "redis://localhost:6379/0")

    # Secret key for sessions or cookies (you can change this)
    SECRET_KEY = os.urandom(24)