/*
 *  PlaylistVideoService
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:25 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistVideoResponse;

public interface PlaylistVideoService {
    PageableResponse<PlaylistVideoResponse> getAllPlaylistVideo(String playlistId, int page, int limit);
}
