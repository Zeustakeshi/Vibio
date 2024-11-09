import redis
import os 
from config.app_config import load_dotenv

redis_client = redis.from_url("redis://default:fGcQSwQeoVFKgkEEjUjxoJTCXBnDWczx@junction.proxy.rlwy.net:41899")

def connect_redis ():
    try:
        redis_client.ping()
        print("connect redis successfull!")
    except redis.ConnectionError:
        print("Failed to connect redis.")