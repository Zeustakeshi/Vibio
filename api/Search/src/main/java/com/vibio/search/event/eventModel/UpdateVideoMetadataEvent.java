/*
 *  NewVideoEvent
 *  @author: Minhhieuano
 *  @created 11/1/2024 12:51 PM
 * */


package com.vibio.search.event.eventModel;

import com.vibio.search.commom.enums.Visibility;
import com.vibio.search.dto.common.Channel;
import lombok.*;

import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoMetadataEvent extends Event {
    @Builder.Default
    private String id = "E_006";
    private String videoId;
    private String title;
    private Set<String> tags;
    private String thumbnail;
    private Visibility visibility;
    private Channel channel;
}
