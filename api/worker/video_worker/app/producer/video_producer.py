import json
import os
from kafka import KafkaProducer

video_producer = KafkaProducer(
    bootstrap_servers= os.getenv("KAFKA_BOOTSTRAP_SERVER"),
    value_serializer=lambda v: json.dumps(v).encode('utf-8') 
)



