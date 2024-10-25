/*
 *  StudioPlaylistVideoService
 *  @author: Minhhieuano
 *  @created 10/20/2024 12:19 AM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.UpdatePlaylistVideoOrderRequest;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistVideoResponse;

public interface StudioPlaylistVideoService {

	PageableResponse<PlaylistVideoResponse> getAllPlaylistVideo(
			String accountId, String playlistId, int page, int limit);

	Integer addVideoToPlaylist(String accountId, String playlistId, String videoId);

	Integer updateVideoOrder(String accountId, String playlistId, UpdatePlaylistVideoOrderRequest request);

	void deleteVideoPlaylist(String accountId, String playlistId, String videoId);
}
