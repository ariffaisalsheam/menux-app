#!/bin/bash

# Build script for Render.com deployment

echo "Starting Menu.X Backend build process..."

# Navigate to backend directory
cd backend

# Make mvnw executable
chmod +x mvnw

# Clean and build the project
echo "Building Spring Boot application..."
./mvnw clean package -DskipTests

echo "Build completed successfully!"
