/*
 *  CommentReaction
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:47 AM
 * */

package com.vibio.video.entity;

import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.entity.key.CommentReactionKey;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentReaction {
	@EmbeddedId
	CommentReactionKey id;

	@ManyToOne
	@MapsId("commentId")
	private Comment comment;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReactionType type;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
