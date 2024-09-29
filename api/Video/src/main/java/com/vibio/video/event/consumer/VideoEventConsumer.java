/*
 *  VideoEventListener
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:11 PM
 * */

package com.vibio.video.event.consumer;

import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventConsumer {

	private final VideoService videoService;

	@EventListener
	public void handleVideoUploadEvent(UploadVideoEvent event) {
		videoService.uploadVideoAsync(event.getVideoId(), event.getChannelId(), event.getVideo());
	}
}
