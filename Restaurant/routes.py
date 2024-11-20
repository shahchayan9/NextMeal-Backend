from flask import Blueprint, jsonify
from app import db  # Ensure db is properly imported
from models import Restaurant  # Ensure Restaurant is properly imported

restaurant_routes = Blueprint('restaurant_routes', __name__)

# Endpoint to get all restaurants
@restaurant_routes.route('/restaurants', methods=['GET'])
def get_restaurants():
    try:
        # Query the database to get all restaurants
        restaurants = Restaurant.query.all()

        # Convert the list of Restaurant objects to a list of dictionaries
        restaurant_list = [
            {
                "id": r.id,
                "name": r.name,
                "address": r.address,
                "city": r.city,
                "state": r.state,
                "postal_code": r.postal_code,
                "latitude": r.latitude,
                "longitude": r.longitude,
                "average_rating": r.average_rating,
                "review_count": r.review_count,
                "is_open": r.is_open,
                "categories": r.categories,
                "hours": r.hours
            }
            for r in restaurants
        ]

        # Return the list as a JSON response
        return jsonify(restaurant_list)

    except Exception as e:
        # Return an error response if something goes wrong
        return jsonify({"error": "Failed to fetch restaurants", "message": str(e)}), 500

# Endpoint to get a particular restaurant by UUID
@restaurant_routes.route('/restaurants/<uuid:restaurant_uuid>', methods=['GET'])
def get_restaurant_by_uuid(restaurant_uuid):
    try:
        # Query the restaurant by UUID
        restaurant = Restaurant.query.filter_by(id=restaurant_uuid).first()

        # If the restaurant is not found, return 404 error
        if restaurant is None:
            return jsonify({"error": "Restaurant not found"}), 404

        # Convert the restaurant object to a dictionary
        restaurant_data = {
            "id": restaurant.id,
            "name": restaurant.name,
            "address": restaurant.address,
            "city": restaurant.city,
            "state": restaurant.state,
            "postal_code": restaurant.postal_code,
            "latitude": restaurant.latitude,
            "longitude": restaurant.longitude,
            "average_rating": restaurant.average_rating,
            "review_count": restaurant.review_count,
            "is_open": restaurant.is_open,
            "categories": restaurant.categories,
            "hours": restaurant.hours
        }

        # Return the restaurant data as a JSON response
        return jsonify(restaurant_data)

    except Exception as e:
        # Return an error response if something goes wrong
        return jsonify({"error": "Failed to fetch restaurant", "message": str(e)}), 500