#!/usr/bin/env sh

set -x  # Print commands and their arguments as they are executed

# Kill the running Docker container named 'my-flask-app'
docker kill my-flask-app

# Remove the Docker container named 'my-flask-app'
docker rm my-flask-app
