/*
 *  NewVideoEvent
 *  @author: Minhhieuano
 *  @created 11/1/2024 12:51 PM
 * */


package com.vibio.video.event.eventModel;

import com.vibio.video.common.enums.Visibility;
import com.vibio.video.dto.response.ChannelResponse;
import lombok.*;

import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMetadataEvent extends Event {
    @Builder.Default
    private String id = "E_006";
    private String videoId;
    private String title;
    private Set<String> tags;
    private String thumbnail;
    private Visibility visibility;
    private ChannelResponse channel;
}
