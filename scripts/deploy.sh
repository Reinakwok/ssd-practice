#!/usr/bin/env sh

set -x  # Print commands and their arguments as they are executed

# Build and run a Docker container for the Flask application
docker build -t my-flask-app .
docker run -d -p 5010:5000 --name my-flask-app my-flask-app

sleep 1  # Wait for 1 second

set +x  # Stop printing commands

# Print instructions
echo 'Now...'
echo 'Visit http://localhost:5001 to see your Flask application in action.'

