/*
 *  UpdateCommentRequest
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:18 PM
 * */

package com.vibio.video.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentRequest {
	@NotNull @NotBlank
	private String content;
}
