/*
 *  CreateCommentRequest
 *  @author: Minhhieuano
 *  @created 10/6/2024 9:21 AM
 * */

package com.vibio.video.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
	@NotEmpty
	@NotNull @Size(max = 2000, message = "Comment too long")
	private String content;

	private String parentId;
}
