/*
 *  FindVideosByIdsRequest
 *  @author: Minhhieuano
 *  @created 10/17/2024 7:24 PM
 * */


package com.vibio.video.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FindVideosByIdsRequest {
    @NotNull
    @Size(min = 1, max = 20)
    private List<String> ids;
}
