from kafka import KafkaConsumer
import json
import os 

from lib.video_processing import handle_video_processing
from event.video_processing_event import Video_processing_event

def listenVideoProcessingEvent ():
    print("start listen video processing topic: ")

    consumer = KafkaConsumer(
        'video_processing',
        bootstrap_servers= os.getenv("KAFKA_BOOTSTRAP_SERVER") ,
        auto_offset_reset='earliest', 
        enable_auto_commit=True,
        group_id='video-worker-group',
        value_deserializer=lambda x: x.decode('utf-8')
    )

    for event in consumer:
       handle_video_processing(Video_processing_event.from_json(event.value))