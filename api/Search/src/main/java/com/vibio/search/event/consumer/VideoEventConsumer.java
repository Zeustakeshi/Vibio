/*
 *  VideoEventListener
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:11 PM
 * */

package com.vibio.search.event.consumer;

import com.vibio.search.event.eventModel.UpdateVideoMetadataEvent;
import com.vibio.search.service.SearchVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventConsumer {
    private final SearchVideoService searchVideoService;

    @KafkaListener(topics = "update_video_metadata", groupId = "${spring.kafka.consumer.group-id}")
    public void NewChannelListener(UpdateVideoMetadataEvent event) {
        searchVideoService.saveVideoMetadata(event);
    }

}
