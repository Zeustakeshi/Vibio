from config.app_config import app_config 
from config.redis_config import connect_redis
from config.cloudinary_config import cloudinary_config
from consumer.video_consumer import listenVideoProcessingEvent

app_config()
connect_redis()
cloudinary_config()
listenVideoProcessingEvent()