/*
 *  UpdatePlaylistRequest
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:44 PM
 * */


package com.vibio.channel.dto.response;

import com.vibio.channel.common.enums.Visibility;
import lombok.Data;

@Data
public class UpdatePlaylistRequest {
    private String name;
    private String description;
    private Visibility visibility;
}
