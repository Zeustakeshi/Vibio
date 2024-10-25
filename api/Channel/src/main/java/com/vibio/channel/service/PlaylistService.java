/*
 *  PlaylistService
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:17 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistResponse;

public interface PlaylistService {
	PlaylistResponse getPublicPlaylistById(String playlistId);

	PageableResponse<PlaylistResponse> getAllPlaylistPublicByChannelId(String channelId, int page, int limit);
}
