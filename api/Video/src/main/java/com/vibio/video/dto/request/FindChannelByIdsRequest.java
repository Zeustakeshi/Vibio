/*
 *  FindChannelByIdsRequest
 *  @author: Minhhieuano
 *  @created 10/7/2024 6:11 PM
 * */

package com.vibio.video.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindChannelByIdsRequest {
	@NotNull @Size(min = 1, max = 100)
	private List<String> ids;
}
