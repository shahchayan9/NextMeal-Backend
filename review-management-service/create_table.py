from dynamodb_config import dynamodb

def create_reviews_table():
    table_name = "reviews"
    existing_tables = [table.name for table in dynamodb.tables.all()]

    if table_name not in existing_tables:
        table = dynamodb.create_table(
            TableName=table_name,
            KeySchema=[
                {"AttributeName": "review_id", "KeyType": "HASH"},  # Partition key
            ],
            AttributeDefinitions=[
                {"AttributeName": "review_id", "AttributeType": "S"},
            ],
            ProvisionedThroughput={
                "ReadCapacityUnits": 5,
                "WriteCapacityUnits": 5,
            },
        )
        print(f"Creating table {table_name}...")
        table.wait_until_exists()
        print(f"Table {table_name} created successfully!")
    else:
        print(f"Table {table_name} already exists.")

if __name__ == "__main__":
    create_reviews_table()
