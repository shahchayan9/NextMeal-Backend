# Use Node.js image as base
FROM node:18

# Set working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port that the application listens on
EXPOSE 3001

# Start the application
CMD ["npm", "start"]
