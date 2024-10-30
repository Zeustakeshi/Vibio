/*
 *  CheckMembershipRequest
 *  @author: Minhhieuano
 *  @created 10/26/2024 1:02 AM
 * */


package com.vibio.video.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckMembershipRequest {

    @NotEmpty
    private String channelId;

    @NotNull
    @Size(min = 1, max = 100)
    private List<String> userIds;
}
