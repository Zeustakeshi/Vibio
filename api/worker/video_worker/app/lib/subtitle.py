from googletrans import Translator
import tempfile


def convert_to_vtt_time_format(seconds):
    minutes, seconds = divmod(seconds, 60)
    hours, minutes = divmod(minutes, 60)
    seconds_int, seconds_frac = divmod(seconds, 1)
    milliseconds = int(seconds_frac * 1000)
    result = f"{int(hours):02}:{int(minutes):02}:{int(seconds_int):02}.{milliseconds:03}"
    return result

def save_subtitles_as_vtt(subtitles, language_code):
    filename = f"./subtitles_{language_code}.vtt"
    with open(filename, "w") as f:
        f.write("WEBVTT\n\n")  
        for segment in subtitles:
            start_time_str = convert_to_vtt_time_format(segment['start'])
            end_time_str = convert_to_vtt_time_format(segment['end'])
            f.write(f"{start_time_str} --> {end_time_str}\n")
            f.write(f"{segment['text']}\n\n")

def translate_subtitles(subtitles, target_language='vi'):
    translator = Translator()
    translated_subtitles = []
    for segment in subtitles:
        translated_text = translator.translate(segment['text'], dest=target_language).text
        translated_subtitles.append({
            'start': segment['start'],
            'end': segment['end'],
            'text': translated_text
        })
    return translated_subtitles

def generate_subtitle_vtt(segments, start_seconds):
    vtt_content = ""
    for segment in segments:
        start_time_str = convert_to_vtt_time_format(segment['start'] + start_seconds)
        end_time_str = convert_to_vtt_time_format(segment['end'] + start_seconds)
        vtt_content += f"{start_time_str} --> {end_time_str}\n"
        vtt_content += f"{segment['text']}\n\n"
    return vtt_content

def mergeSubtitleSegment(segments):
    with tempfile.NamedTemporaryFile(delete=False, suffix=".vtt", mode= 'w') as tmp_file:
        tmp_file_path = tmp_file.name
        tmp_file.write("WEBVTT\n\n")  
        for segment in segments:
            tmp_file.write(segment + "\n\n") 
        return tmp_file_path;