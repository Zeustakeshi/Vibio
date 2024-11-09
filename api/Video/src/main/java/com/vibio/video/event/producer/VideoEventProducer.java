/*
 *  VideoEventProducer
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:13 PM
 * */

package com.vibio.video.event.producer;

import com.vibio.video.event.eventModel.ReactionVideoEvent;
import com.vibio.video.event.eventModel.UpdateMetadataEvent;
import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.event.eventModel.UploadVideoThumbnailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventProducer {
    private final ApplicationEventPublisher publisher;
    private final KafkaTemplate<String, Object> videoTemplate;

    public void uploadVideo(UploadVideoEvent event) {
        publisher.publishEvent(event);
    }

    public void uploadVideoThumbnail(UploadVideoThumbnailEvent event) {
        publisher.publishEvent(event);
    }

    public void reactionVideo(ReactionVideoEvent event) {
        videoTemplate.send("video_reaction", event);

    }
    
    public void updateMetadata(UpdateMetadataEvent event) {
        videoTemplate.send("update_video_metadata", event);
    }

}
