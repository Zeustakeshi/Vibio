/*
 *  VideoEventProducer
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:13 PM
 * */

package com.vibio.video.event.producer;

import com.vibio.video.event.eventModel.UploadVideoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventProducer {
	private final ApplicationEventPublisher publisher;

	public void uploadVideo(UploadVideoEvent event) {
		publisher.publishEvent(event);
	}
}
