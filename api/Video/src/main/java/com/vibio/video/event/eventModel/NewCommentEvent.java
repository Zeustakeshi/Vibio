/*
 *  NewCommentEvent
 *  @author: Minhhieuano
 *  @created 10/12/2024 11:34 PM
 * */


package com.vibio.video.event.eventModel;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentEvent extends Event {
    @Builder.Default
    private String id = "E_003";
    private String videoId;
    private String parentId;
    private String commentId;
    private String userId;
}
