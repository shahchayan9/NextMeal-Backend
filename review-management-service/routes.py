from flask import Blueprint, request, jsonify
from dynamodb_operations import create_review, get_review, delete_review, get_reviews_with_usernames, update_review_in_dynamodb
review_routes = Blueprint('review_routes', __name__)

# Add a new review
@review_routes.route('/reviews', methods=['POST'])
def add_review():
    review_data = request.json
    print(f"Received review data: {review_data}")  # Log the incoming data
    response = create_review(review_data)
    if response:
        print(f"Review successfully added. DynamoDB response: {response}")  # Log success
        return jsonify({"message": "Review added successfully"}), 201
    else:
        print("Failed to add review.")  # Log failure
        return jsonify({"error": "Failed to add review"}), 500


# Get a review by ID
@review_routes.route('/reviews/<string:review_id>', methods=['GET'])
def get_single_review(review_id):
    review = get_review(review_id)
    if review:
        return jsonify(review)
    else:
        return jsonify({"error": "Review not found"}), 404


# Get all reviews for a business
# @review_routes.route('/reviews/business/<string:business_id>', methods=['GET'])
# def get_reviews_for_business(business_id):
#     reviews = get_reviews_by_business(business_id)
#     if reviews:
#         return jsonify(reviews)
#     else:
#         return jsonify({"error": "No reviews found for this business"}), 404
    
@review_routes.route('/reviews/business/<string:business_id>', methods=['GET'])
def get_reviews_for_business_with_usernames(business_id):
    reviews = get_reviews_with_usernames(business_id)
    if reviews:
        return jsonify(reviews)
    else:
        return jsonify({"error": "No reviews found for this business"}), 404



# Delete a review
@review_routes.route('/reviews/<string:review_id>', methods=['DELETE'])
def delete_single_review(review_id):
    response = delete_review(review_id)
    if response:
        return jsonify({"message": "Review deleted successfully"})
    else:
        return jsonify({"error": "Failed to delete review"}), 500

# Update a review
@review_routes.route('/reviews/<string:review_id>', methods=['PUT'])
def update_review(review_id):
    updated_data = request.json  # Get the updated data from the request body
    response = update_review_in_dynamodb(review_id, updated_data)
    if response:
        return jsonify({"message": "Review updated successfully"}), 200
    else:
        return jsonify({"error": "Failed to update review"}), 500
