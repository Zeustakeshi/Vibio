from dataclasses import dataclass, field
import json
from datetime import datetime
from event.event import Event
import uuid

@dataclass
class Video_processing_event(Event):
    key: str 
    video_id: str
    video_url: str
    start_time: int
    end_time: int
    size: int
    order: int
    id: str = field(default="E_009") 

