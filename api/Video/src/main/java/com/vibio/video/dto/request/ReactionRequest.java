/*
 *  VideoReactionRequest
 *  @author: Minhhieuano
 *  @created 10/13/2024 11:03 AM
 * */


package com.vibio.video.dto.request;

import com.vibio.video.common.enums.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionRequest {
    @NotNull
    private ReactionType reactionType;
}
