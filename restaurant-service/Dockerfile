# Base image for Python
FROM python:3.9-slim

# Set working directory in the container
WORKDIR /app

# Install system dependencies required to build psycopg2
RUN apt-get update && apt-get install -y --no-install-recommends \
    gcc \
    libpq-dev \
    python3-dev \
    musl-dev \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Copy only the files needed for dependencies first
COPY requirements.txt .

# Install Python dependencies
RUN pip3 install --no-cache-dir -r requirements.txt

# Copy the rest of the application code into the container
COPY . .

# Expose the port the service will run on
EXPOSE 5004

# Command to run the app
CMD ["python3", "app.py"]
