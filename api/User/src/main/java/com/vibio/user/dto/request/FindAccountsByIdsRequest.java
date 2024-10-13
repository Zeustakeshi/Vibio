/*
 *  FindAccountsByIds
 *  @author: Minhhieuano
 *  @created 10/10/2024 9:00 AM
 * */

package com.vibio.user.dto.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class FindAccountsByIdsRequest {
	@Size(min = 1, max = 100)
	private List<String> ids;
}
