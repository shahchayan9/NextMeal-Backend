from flask import Flask, g, request, Response
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS
from db import db
import os
from dotenv import load_dotenv
from prometheus_client import generate_latest, CONTENT_TYPE_LATEST
from metrics import REQUEST_COUNT, REQUEST_LATENCY  # Import metrics here

load_dotenv()

def before_request():
    # Using time.time() for simplicity
    import time
    g.start_time = time.time()

def after_request(response):
    import time
    latency = time.time() - g.start_time
    REQUEST_LATENCY.labels(request.path).observe(latency)
    REQUEST_COUNT.labels(request.method, request.path).inc()
    return response

def create_app():
    app = Flask(__name__)
    CORS(app)
    app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv('DB_URL')
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    db.init_app(app)

    from routes import restaurant_routes
    app.register_blueprint(restaurant_routes)

    app.before_request(before_request)
    app.after_request(after_request)

    @app.route('/metrics')
    def metrics():
        return Response(generate_latest(), mimetype=CONTENT_TYPE_LATEST)

    @app.route('/')
    def index():
        return 'Welcome to the Restaurant API!'

    return app

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True, host='0.0.0.0', port=5004)
