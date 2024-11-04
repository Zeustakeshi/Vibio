/*
 *  VideoEventListener
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:11 PM
 * */

package com.vibio.video.event.consumer;

import com.vibio.video.event.eventModel.ReactionVideoEvent;
import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.event.eventModel.UploadVideoThumbnailEvent;
import com.vibio.video.service.StudioVideoService;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventConsumer {

    private final StudioVideoService studioVideoService;
    private final VideoService videoService;

    @EventListener
    public void handleUploadVideoEvent(UploadVideoEvent event) {
        studioVideoService.uploadVideoAsync(
                event.getVideoId(), event.getChannel(), event.getAccountId(), event.getVideo());
    }

    @EventListener
    public void handleUploadVideoThumbnail(UploadVideoThumbnailEvent event) {
        studioVideoService.uploadThumbnailAsync(
                event.getVideoId(), event.getChannelId(), event.getAccountId(), event.getThumbnail());
    }

    @KafkaListener(topics = "video_reaction", groupId = "${spring.kafka.consumer.group-id}")
    public void reactionVideoEventListener(ReactionVideoEvent event) {
        videoService.updateVideoReactionCount(event.getVideoId());
    }
}
