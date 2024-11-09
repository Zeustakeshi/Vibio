# Stage 1: Build stage
FROM python:3.9-slim as builder

# Install ffmpeg and dependencies needed to build the Python packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends ffmpeg gcc libpq-dev && \
    rm -rf /var/lib/apt/lists/*

RUN which ffmpeg 

# Set work directory and copy requirements for installation
WORKDIR /app
COPY requirements.txt /app/

# Install Python packages without cache to reduce image size
RUN pip install --no-cache-dir -r requirements.txt

# Stage 2: Final stage - lightweight Alpine image
FROM python:3.9-alpine

# Copy necessary binaries and Python packages from the build stage
COPY --from=builder /usr/bin/ffmpeg /usr/local/bin/
COPY --from=builder /usr/lib/python3.9/site-packages /usr/local/lib/python3.9/site-packages

# Set the work directory
WORKDIR /app

# Copy the application source code
COPY ./app /app

# Define command to run the application
CMD ["python", "main.py"]
