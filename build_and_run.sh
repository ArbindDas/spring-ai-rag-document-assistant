#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

echo "🔹 Starting Maven build..."
mvn clean install

echo "🔹 Bringing down any running Docker containers..."
docker compose down

echo "🔹 Building and starting Docker containers..."
docker compose up --build 

echo "✅ All done! Containers are up and running."
