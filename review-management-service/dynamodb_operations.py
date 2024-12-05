import os
import boto3
from botocore.exceptions import ClientError
from decimal import Decimal
import traceback

# Load environment variables
from dotenv import load_dotenv
load_dotenv()

# Get environment variables
TABLE_NAME = os.getenv("DYNAMODB_TABLE")
AWS_REGION = os.getenv("AWS_REGION")

# Initialize DynamoDB resource
dynamodb = boto3.resource(
    'dynamodb',
    region_name=AWS_REGION,
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY")
)

# Reference the table
table = dynamodb.Table(TABLE_NAME)


# Create a review
def create_review(review_data):
    try:
        # Debug: Print raw review data
        print("Received review data:", review_data)

        # Convert floats to Decimal
        for key, value in review_data.items():
            if isinstance(value, float):
                review_data[key] = Decimal(str(value))
        
        # Debug: Print transformed review data
        print("Review data after conversion:", review_data)

        # Add the item to DynamoDB
        response = table.put_item(Item=review_data)
        return response
    except ClientError as e:
        print(f"Error adding review: {e.response['Error']['Message']}")
        return None
    except Exception as e:
        print(f"Unexpected error: {str(e)}")
        return None



# Get a review by review_id
def get_review(review_id):
    try:
        response = table.get_item(Key={'review_id': review_id})
        return response.get('Item')
    except ClientError as e:
        print(f"Error fetching review: {e}")
        return None


# Get all reviews for a business_id
def get_reviews_by_business(business_id):
    try:
        response = table.query(
            IndexName="business_id-index",  # Add a GSI if necessary
            KeyConditionExpression=boto3.dynamodb.conditions.Key('business_id').eq(business_id)
        )
        return response.get('Items')
    except ClientError as e:
        print(f"Error fetching reviews: {e}")
        return None


# Delete a review
def delete_review(review_id):
    try:
        response = table.delete_item(Key={'review_id': review_id})
        return response
    except ClientError as e:
        print(f"Error deleting review: {e}")
        return None
