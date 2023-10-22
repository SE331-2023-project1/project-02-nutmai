# Use the official Node.js base image
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install project dependencies
RUN npm install

# Copy the rest of your Vue.js app
COPY . .

# Expose the port your Vue app is listening on
EXPOSE 5173

# Command to run your Vue.js app
CMD ["npm", "run", "serve"]
