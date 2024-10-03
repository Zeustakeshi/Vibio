/*
 *  UpdateVideoRequest
 *  @author: Minhhieuano
 *  @created 10/3/2024 10:30 AM
 * */

package com.vibio.video.dto.request;

import com.vibio.video.common.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateVideoRequest {

	@NotBlank
	@NotNull @Size(min = 4, message = "Title size too short")
	@Size(max = 500, message = "Title size too long")
	private String title;

	@NotBlank
	@NotNull @Size(min = 8, message = "Description size too short")
	@Size(max = 500, message = "Description size too long")
	private String description;

	@NotNull private Visibility visibility;

	@NotNull private boolean allowedComment;

	@NotNull private Set<String> tags;
}
