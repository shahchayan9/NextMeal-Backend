from flask import Blueprint, jsonify, request
from app import db  # Ensure db is properly imported
from models import Restaurant, RestaurantImage  # Ensure Restaurant and RestaurantImage are properly imported
from sqlalchemy.orm import aliased  # For handling joins
import redis
import json
import os
from prometheus_client import Counter
from dotenv import load_dotenv
load_dotenv()

RESTAURANTS_API_HITS = Counter(
    'restaurants_api_hits_total',
    'Total number of hits to the /restaurants API'
)

redis_client = redis.StrictRedis(
    host=os.getenv('REDIS_HOST'),
    port=int(os.getenv('REDIS_PORT', 6379)),
    ssl=True,
    ssl_cert_reqs=None,
    decode_responses=True
)

restaurant_routes = Blueprint('restaurant_routes', __name__)

# Endpoint to get all restaurants with filters
@restaurant_routes.route('/restaurants', methods=['GET'])
def get_restaurants():
    try:

        # Increment the counter whenever this endpoint is hit
        RESTAURANTS_API_HITS.inc()

        # Get filter parameters from the request (if they exist)
        filters = {}
        name = request.args.get('name')
        city = request.args.get('city')
        state = request.args.get('state')
        min_rating = request.args.get('min_rating', type=float)
        max_rating = request.args.get('max_rating', type=float)

        # Build the filters dictionary
        if name:
            filters["name"] = name
        if city:
            filters["city"] = city
        if state:
            filters["state"] = state
        if min_rating is not None:
            filters["min_rating"] = min_rating
        if max_rating is not None:
            filters["max_rating"] = max_rating

        cache_key = f"restaurants:{json.dumps(filters, sort_keys=True)}"

        # Check if the data exists in Redis cache
        cached_data = redis_client.get(cache_key)
        if cached_data:
            print("Cache Hit!")
            return jsonify(json.loads(cached_data))

        print("Cache Miss!")

        # Build the query to filter restaurants based on provided filters
        query = db.session.query(Restaurant, RestaurantImage.photo_id) \
            .outerjoin(RestaurantImage, Restaurant.business_id == RestaurantImage.business_id)

        # Apply filters dynamically to the query
        if "name" in filters:
            query = query.filter(Restaurant.name.ilike(f"%{filters['name']}%"))
        if "city" in filters:
            query = query.filter(Restaurant.city.ilike(f"%{filters['city']}%"))
        if "state" in filters:
            query = query.filter(Restaurant.state.ilike(f"%{filters['state']}%"))
        if "min_rating" in filters:
            query = query.filter(Restaurant.average_rating >= filters["min_rating"])
        if "max_rating" in filters:
            query = query.filter(Restaurant.average_rating <= filters["max_rating"])

        # Execute the query and fetch the results
        restaurants = query.all()

        # Convert the list of Restaurant objects to a list of dictionaries
        restaurant_list = [
            {
                "business_id": r[0].business_id,
                "name": r[0].name,
                "address": r[0].address,
                "city": r[0].city,
                "state": r[0].state,
                "postal_code": r[0].postal_code,
                "latitude": r[0].latitude,
                "longitude": r[0].longitude,
                "average_rating": r[0].average_rating,
                "review_count": r[0].review_count,
                "is_open": r[0].is_open,
                "categories": r[0].categories,
                "hours": r[0].hours,
                "attributes": r[0].attributes,  # Include attributes in the response
                "image": r[1]  # Image URL or photo_id
            }
            for r in restaurants
        ]

         # Store the result in Redis with a TTL of 5 minutes
        redis_client.setex(cache_key, 300, json.dumps(restaurant_list))

        # Return the list as a JSON response
        return jsonify(restaurant_list)

    except Exception as e:
        # Return an error response if something goes wrong
        return jsonify({"error": "Failed to fetch restaurants", "message": str(e)}), 500

# Endpoint to get a particular restaurant by business_id with image
@restaurant_routes.route('/restaurants/<string:business_id>', methods=['GET'])
def get_restaurant_by_business_id(business_id):
    try:
        # Create an alias for the restaurant_images table
        restaurant_images_alias = aliased(RestaurantImage)

        # Query the restaurant and its image by business_id
        restaurant = db.session.query(Restaurant, restaurant_images_alias.photo_id) \
            .outerjoin(restaurant_images_alias, Restaurant.business_id == restaurant_images_alias.business_id) \
            .filter(Restaurant.business_id == business_id) \
            .first()

        # If the restaurant is not found, return 404 error
        if restaurant is None:
            return jsonify({"error": "Restaurant not found"}), 404

        # Convert the restaurant object and image data to a dictionary
        restaurant_data = {
            "business_id": restaurant[0].business_id,
            "name": restaurant[0].name,
            "address": restaurant[0].address,
            "city": restaurant[0].city,
            "state": restaurant[0].state,
            "postal_code": restaurant[0].postal_code,
            "latitude": restaurant[0].latitude,
            "longitude": restaurant[0].longitude,
            "average_rating": restaurant[0].average_rating,
            "review_count": restaurant[0].review_count,
            "is_open": restaurant[0].is_open,
            "categories": restaurant[0].categories,
            "hours": restaurant[0].hours,
            "attributes": restaurant[0].attributes,  # Include attributes in the response
            "image": restaurant[1]  # Image URL or photo_id
        }

        # Return the restaurant data as a JSON response
        return jsonify(restaurant_data)

    except Exception as e:
        # Return an error response if something goes wrong
        return jsonify({"error": "Failed to fetch restaurant", "message": str(e)}), 500
