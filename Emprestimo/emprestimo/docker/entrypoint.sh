#!/bin/bash

set -e
set -x

echo "Checking migrations..."
if [[ $(alembic check) =~ "FAILED: New upgrade"* ]]; then
    echo "Generating migrations..."
    alembic-autogen-check --config ./alembic.ini ||
    file_count=$(ls -1 ./alembic/versions | wc -l)
    file_count=$(printf "%03d" "$file_count")
    alembic revision --autogenerate -m "$file_count"
fi

echo "Applying migrations..."
alembic upgrade head

echo "Starting server as `whoami`"
uvicorn main:app --host 0.0.0.0 --port 8000 --reload
