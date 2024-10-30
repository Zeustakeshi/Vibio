/*
 *  FindAccountsByIdsRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:12 PM
 * */


package com.vibio.channel.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FindAccountsByIdsRequest {
    @Size(min = 1, max = 100)
    private List<String> ids;
}