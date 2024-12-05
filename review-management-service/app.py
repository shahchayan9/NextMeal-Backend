from flask import Flask
from flask_cors import CORS
from routes import review_routes  # Import the routes

def create_app():
    app = Flask(__name__)
    CORS(app)

    # Register the routes
    app.register_blueprint(review_routes)

    @app.route('/')
    def index():
        return {"message": "Reviews microservice is running."}

    return app


if __name__ == '__main__':
    app = create_app()
    app.run(debug=True, host='0.0.0.0', port=5001)
