from dataclasses import dataclass, field
from typing import List
from datetime import datetime
from event.event import Event
import uuid

@dataclass
class Update_subtitle_event (Event):
    video_id: str
    origin_subtitle: str
    translates: List[str] 
    total_segment: int 
    id: str = field(default="E_010")
