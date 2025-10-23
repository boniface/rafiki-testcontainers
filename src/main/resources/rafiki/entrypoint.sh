#!/bin/sh
set -e

# Wait for kratos to be ready
echo "Starting Kratos..."

# Run migrations
kratos migrate sql -e --yes

# Start Kratos
exec kratos serve -c /etc/config/kratos/kratos.yml --dev --watch-courier
