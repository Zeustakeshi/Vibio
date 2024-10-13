/*
 *  CommentEventListener
 *  @author: Minhhieuano
 *  @created 10/12/2024 11:44 PM
 * */

package com.vibio.video.event.consumer;

import com.vibio.video.event.eventModel.NewCommentEvent;
import com.vibio.video.service.CommentService;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

	private final VideoService videoService;
	private final CommentService commentService;

	@KafkaListener(topics = "new_comment", groupId = "${spring.kafka.consumer.group-id}")
	public void newCommentListener(NewCommentEvent event) {
		// update comment count for video
		videoService.updateCommentCount(event.getVideoId());

		// update reply count for video
		if (event.getParentId() == null) return;
		commentService.updateReplyCount(event.getParentId());
	}
}
