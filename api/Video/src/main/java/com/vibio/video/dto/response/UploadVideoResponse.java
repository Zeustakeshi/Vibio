/*
 *  UploadVideoResponse
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:18 PM
 * */

package com.vibio.video.dto.response;

import com.vibio.video.common.enums.UploadStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVideoResponse {
	private String videoId;
	private UploadStatus uploadStatus;
}
