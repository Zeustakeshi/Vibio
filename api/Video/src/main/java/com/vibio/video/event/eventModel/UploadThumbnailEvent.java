/*
 *  UploadThumbnailEvent
 *  @author: Minhhieuano
 *  @created 10/3/2024 11:29 AM
 * */

package com.vibio.video.event.eventModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UploadThumbnailEvent extends Event {
    private byte[] thumbnail;
    private String videoId;
    private String channelId;
    private String accountId;
}
