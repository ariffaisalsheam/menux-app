#!/bin/bash

# Start script for Render.com deployment

echo "Starting Menu.X Backend server..."

# Navigate to backend directory
cd backend

# Start the Spring Boot application
echo "Running Spring Boot application..."
java -jar target/menu-x-backend-1.0.0.jar
