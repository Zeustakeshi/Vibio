/*
 *  CommentEventProducer
 *  @author: Minhhieuano
 *  @created 10/12/2024 11:29 PM
 * */

package com.vibio.video.event.producer;

import com.vibio.video.event.eventModel.NewCommentEvent;
import com.vibio.video.event.eventModel.ReactionCommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventProducer {
	private final KafkaTemplate<String, Object> commentTemplate;

	public void createNewComment(NewCommentEvent event) {
		commentTemplate.send("new_comment", event);
	}

	public void reactionComment(ReactionCommentEvent event) {
		commentTemplate.send("comment_reaction", event);
	}
}
