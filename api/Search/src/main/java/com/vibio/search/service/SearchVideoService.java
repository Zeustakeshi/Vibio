/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:17 PM
 * */


package com.vibio.search.service;

import com.vibio.search.dto.response.PageableResponse;
import com.vibio.search.dto.response.SearchVideoResponse;
import com.vibio.search.event.eventModel.UpdateVideoMetadataEvent;

import java.util.List;

public interface SearchVideoService {
    void saveVideoMetadata(UpdateVideoMetadataEvent event);

    List<String> searchVideoAutoComplete(String query);

    PageableResponse<SearchVideoResponse> searchVideo(String query, int page, int limit);
}
