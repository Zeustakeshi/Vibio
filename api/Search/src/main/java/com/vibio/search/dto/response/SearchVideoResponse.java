/*
 *  SearchVideoResponse
 *  @author: Minhhieuano
 *  @created 11/2/2024 9:06 PM
 * */


package com.vibio.search.dto.response;

import com.vibio.search.dto.common.Channel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SearchVideoResponse {
    private String id;
    private String title;
    private String thumbnail;
    private Channel channel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
