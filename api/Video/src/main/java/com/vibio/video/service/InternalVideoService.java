/*
 *  InternalVideoService
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:52 PM
 * */

package com.vibio.video.service;

import com.vibio.video.dto.request.FindVideosByIdsRequest;
import com.vibio.video.dto.response.VideoResponse;

import java.util.List;

public interface InternalVideoService {
    boolean introspectVideo(String videoId);

    List<VideoResponse> getAllVideoByIds(FindVideosByIdsRequest request);
}
