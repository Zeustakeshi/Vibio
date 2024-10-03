/*
 *  UploadThumbnailEvent
 *  @author: Minhhieuano
 *  @created 10/3/2024 11:29 AM
 * */

package com.vibio.video.event.eventModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadThumbnailEvent {
	private byte[] thumbnail;
	private String videoId;
	private String channelId;
	private String accountId;
}
