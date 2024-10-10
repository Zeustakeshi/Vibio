/*
 *  UploadVideoEvent
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:09 PM
 * */

package com.vibio.video.event.eventModel;

import com.vibio.video.dto.response.ChannelDetailResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVideoEvent {
	private byte[] video;
	private String videoId;
	private ChannelDetailResponse channel;
	private String accountId;
}
