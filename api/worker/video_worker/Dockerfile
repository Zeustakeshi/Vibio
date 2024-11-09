# Single-stage Dockerfile using a slim Python image
FROM python:3.9-slim

# Install ffmpeg, gcc, libpq-dev, and other dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends ffmpeg gcc libpq-dev && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory and copy requirements for installation
WORKDIR /app
COPY requirements.txt /app/

# Install Python packages without caching to reduce image size
RUN pip install --no-cache-dir -r requirements.txt

# Copy the application source code
COPY ./app /app

CMD ["python", "main.py"]
