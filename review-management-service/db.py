import boto3
import os
from dotenv import load_dotenv

load_dotenv()  # Load environment variables

# DynamoDB Client
dynamodb = boto3.resource(
    'dynamodb',
    aws_access_key_id=os.getenv('AWS_ACCESS_KEY_ID'),
    aws_secret_access_key=os.getenv('AWS_SECRET_ACCESS_KEY'),
    region_name=os.getenv('AWS_REGION')
)

reviews_table = dynamodb.Table(os.getenv('REVIEWS_TABLE'))
