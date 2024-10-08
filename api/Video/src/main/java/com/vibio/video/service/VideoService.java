/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:55 PM
 * */


package com.vibio.video.service;


import com.vibio.video.dto.response.PageableResponse;
import com.vibio.video.dto.response.VideoResponse;

public interface VideoService {
    PageableResponse<VideoResponse> getFeeds(String accountId, int page, int limit);

    PageableResponse<VideoResponse> getGuestFeeds(int page, int limit);
}
