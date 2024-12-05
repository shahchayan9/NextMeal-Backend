import boto3
import os

# Load environment variables
from dotenv import load_dotenv
load_dotenv()

# DynamoDB configuration
DYNAMODB_ENDPOINT = os.getenv("DYNAMODB_ENDPOINT", "http://localhost:8000")
DYNAMODB_REGION = os.getenv("DYNAMODB_REGION", "us-west-1")

# Create DynamoDB resource
dynamodb = boto3.resource(
    "dynamodb",
    endpoint_url=DYNAMODB_ENDPOINT,
    region_name=DYNAMODB_REGION
)
