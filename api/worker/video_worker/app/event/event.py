from dataclasses import dataclass, field,  asdict, fields
from datetime import datetime
import json
import re

def to_camel_case(snake_str):
    parts = snake_str.split('_')
    return parts[0] + ''.join(word.capitalize() for word in parts[1:])

def to_snake_case (name: str) -> str:
    return re.sub(r'([a-z])([A-Z])', r'\1_\2', name).lower()


@dataclass
class Event:
    @classmethod
    def from_json(cls: type, json_str: str):
        data = json.loads(json_str)
        snake_case_data = {to_snake_case(k): v for k, v in data.items()}
        field_names = {f.name for f in fields(cls)}
        filtered_data = {k: v for k, v in snake_case_data.items() if k in field_names}
        return cls(**filtered_data)

    def to_dict(self):
        original_dict = asdict(self)
        camel_case_dict = {to_camel_case(k): v for k, v in original_dict.items()}
        return camel_case_dict