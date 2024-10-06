/*
 *  VideoEventListener
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:11 PM
 * */

package com.vibio.video.event.consumer;

import com.vibio.video.event.eventModel.UploadThumbnailEvent;
import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.service.StudioVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventConsumer {

	private final StudioVideoService studioVideoService;

	@EventListener
	public void handleUploadVideoEvent(UploadVideoEvent event) {
		studioVideoService.uploadVideoAsync(
				event.getVideoId(), event.getChannel(), event.getAccountId(), event.getVideo());
	}

	@EventListener
	public void handleUploadThumbnailEvent(UploadThumbnailEvent event) {
		studioVideoService.uploadThumbnailAsync(
				event.getVideoId(), event.getChannelId(), event.getAccountId(), event.getThumbnail());
	}
}
