/*
 *  PlaylistResponse
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:15 PM
 * */

package com.vibio.channel.dto.response;

import com.vibio.channel.common.enums.Visibility;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistResponse {
	private String id;
	private String name;
	private String description;
	private String defaultThumbnail;
	private Integer videoCount;
	private Visibility visibility;
	private LocalDateTime updatedAt;
}
