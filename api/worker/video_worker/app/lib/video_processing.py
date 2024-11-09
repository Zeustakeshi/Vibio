import ffmpeg
import tempfile
import whisper
import torch
import os 



from config.redis_config import redis_client
from config.cloudinary_config import cloudinary
from common import status
from event.video_processing_event import Video_processing_event
from event.update_subtitle_event import Update_subtitle_event
from lib.subtitle import generate_subtitle_vtt, mergeSubtitleSegment
from producer.video_producer import video_producer

device = "cuda" if torch.cuda.is_available() else "cpu"
model = whisper.load_model("base").to(device)

def load_video_and_save_temp_file (video_url, start_time, end_time): 
    try:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".mp4") as tmp_file:
            tmp_file_path = tmp_file.name
            (
                ffmpeg
                .input(video_url, ss=start_time, to=end_time)
                .output(tmp_file_path, c='copy')
                .run(overwrite_output=True, quiet=True)
            )
            return tmp_file_path;
    except ffmpeg.Error as e:
        print(f" ffmpeg error: {e}")

def extract_video_audio (tmp_file_path ):
    audio_file = tempfile.NamedTemporaryFile(delete=False, suffix=".wav")
    audio_file_path = audio_file.name
    try:
        ffmpeg.input(tmp_file_path).output(audio_file_path, format='wav').run(overwrite_output=True)
        return audio_file_path
    except ffmpeg.Error as e:
        print(f"ffmpeg error: {e.stderr}")

def pick_processing_segment (video_id: str, key: str ):
    redis_client.hset("video_processing_" + video_id,   key, status.PENDING)

def can_process_segment(video_id: str, key: str) -> bool:
    process_value = redis_client.hget("video_processing_" + video_id, key)
    if process_value is not None:
        process_value = process_value.decode("utf-8").strip()
        return int(process_value) == status.FAILED
    return True


def handle_video_processing (event: Video_processing_event):  
    if not can_process_segment(event.video_id, event.key):
        print("pocess_" + event.key + " it not empty")
        return 
    pick_processing_segment(event.video_id, event.key)

    tmp_file_path = load_video_and_save_temp_file(
        video_url= event.video_url, 
        start_time= event.start_time,
        end_time= event.end_time
    )
    audio_file_path = extract_video_audio(tmp_file_path)
    result = model.transcribe(audio_file_path)
    segments = result['segments']
    vtt_content = generate_subtitle_vtt(segments, event.start_time)
    redis_client.zadd("video_" + event.video_id, {vtt_content: event.order})
    redis_client.hset("video_processing_" + event.video_id,   event.key,  status.SUCCESS)
    os.remove(tmp_file_path)
    os.remove(audio_file_path)
    print(f"complete {event.order}/{event.size}")
    if redis_client.zcard("video_" + event.video_id) == event.size :
        items = redis_client.zrange("video_" + event.video_id, 0, -1)
        segments = [item.decode('utf-8') for item in items]

        print("create subtitle temp file")
        subtile_file_path =  mergeSubtitleSegment(segments)

        redis_client.delete("video_processing_" + event.video_id)
        redis_client.delete("video_" + event.video_id)

        #save subtitle to cloudinary

        print("saving file ....")
        upload_response =  cloudinary.uploader.upload(
            subtile_file_path, 
            resource_type = "raw",
            public_id= event.video_id + "_origin" , 
            folder = "/vibio/videos/subtitles/"
        )

        # update origin subtile to video service
        print("update video subtitle ....")
        update_subtitle_event = Update_subtitle_event(
            origin_subtitle = upload_response["secure_url"],
            total_segment= event.size,
            translates= [],
            video_id=event.video_id
        )
        video_producer.send("update_video_subtitle" ,update_subtitle_event.to_dict())
        os.remove(subtile_file_path)
    




