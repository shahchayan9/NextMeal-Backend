import os
import boto3
from botocore.exceptions import ClientError
from decimal import Decimal
import traceback
from decimal import Decimal
import uuid
from boto3.dynamodb.conditions import Attr
import psycopg2
from psycopg2.extras import RealDictCursor

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

        # Ensure a unique review_id is generated if not provided
        if 'review_id' not in review_data or not review_data['review_id']:
            review_data['review_id'] = str(uuid.uuid4())  # Generate a unique UUID

        # Convert specific fields to Decimal where required
        if 'stars' in review_data:
            review_data['stars'] = Decimal(str(review_data['stars']))  # Convert stars to Decimal

        # Convert floats in other fields to Decimal, if any
        for key, value in review_data.items():
            if isinstance(value, float):
                review_data[key] = Decimal(str(value))
        
        # Debug: Print transformed review data
        print("Review data after conversion:", review_data)

        # Add the item to DynamoDB
        response = table.put_item(Item=review_data)
        print("Review successfully added. DynamoDB response:", response)
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


# PostgreSQL connection settings
POSTGRES_HOST = os.getenv("DB_HOST")
POSTGRES_DB = os.getenv("DB_NAME")
POSTGRES_USER = os.getenv("DB_USER")
POSTGRES_PASSWORD = os.getenv("DB_PASSWORD")

def get_reviews_with_usernames(business_id):
    try:
        # Step 1: Fetch reviews from DynamoDB
        dynamo_response = table.query(
            IndexName="business_id-index",  # Ensure this GSI exists
            KeyConditionExpression=boto3.dynamodb.conditions.Key('business_id').eq(business_id)
        )
        reviews = dynamo_response.get('Items', [])

        # Step 2: Connect to PostgreSQL
        connection = psycopg2.connect(
            host=POSTGRES_HOST,
            database=POSTGRES_DB,
            user=POSTGRES_USER,
            password=POSTGRES_PASSWORD
        )
        cursor = connection.cursor(cursor_factory=RealDictCursor)

        # Step 3: Fetch usernames for all user_ids in reviews
        user_ids = [review['user_id'] for review in reviews]
        if user_ids:
            query = f"SELECT user_id, name FROM users WHERE user_id = ANY(%s)"
            cursor.execute(query, (user_ids,))
            user_data = {row['user_id']: row['name'] for row in cursor.fetchall()}

            # Step 4: Add user names to reviews
            for review in reviews:
                review['user_name'] = user_data.get(review['user_id'], "Unknown")

        cursor.close()
        connection.close()

        return reviews
    except Exception as e:
        print(f"Error fetching reviews with usernames: {str(e)}")
        return []



# Delete a review
def delete_review(review_id):
    try:
        response = table.delete_item(Key={'review_id': review_id})
        return response
    except ClientError as e:
        print(f"Error deleting review: {e}")
        return None


# Update a review
def update_review_in_dynamodb(review_id, updated_data):
    try:
        # Prepare the update expression and attribute values
        update_expression = "SET " + ", ".join(f"#{key} = :{key}" for key in updated_data.keys())
        expression_attribute_names = {f"#{key}": key for key in updated_data.keys()}
        expression_attribute_values = {f":{key}": Decimal(str(value)) if isinstance(value, (int, float)) else value for key, value in updated_data.items()}
        
        # Perform the update operation
        response = table.update_item(
            Key={'review_id': review_id},
            UpdateExpression=update_expression,
            ExpressionAttributeNames=expression_attribute_names,
            ExpressionAttributeValues=expression_attribute_values,
            ReturnValues="ALL_NEW"  # Return the updated item
        )
        print("Update successful. DynamoDB response:", response)
        return response.get('Attributes')  # Return the updated attributes
    except ClientError as e:
        print(f"Error updating review: {e.response['Error']['Message']}")
        return None
    except Exception as e:
        print(f"Unexpected error: {str(e)}")
        traceback.print_exc()  # Debugging unexpected errors
        return None
