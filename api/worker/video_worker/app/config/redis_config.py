import redis
import os 
from config.app_config import load_dotenv

redis_client = redis.from_url(os.getenv("REDIS_URL"))

def connect_redis ():
    try:
        redis_client.ping()
        print("connect redis successfull!")
    except redis.ConnectionError:
        print("Failed to connect redis.")