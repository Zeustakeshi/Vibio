/*
 *  StudioPlaylistService
 *  @author: Minhhieuano
 *  @created 10/20/2024 12:15 AM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.PlaylistRequest;
import com.vibio.channel.dto.request.UpdatePlaylistRequest;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistResponse;

public interface StudioPlaylistService {

    PageableResponse<PlaylistResponse> getAllByAccountId(String accountId, int page, int limit);

    PlaylistResponse createPlaylist(String accountId, PlaylistRequest request);

    PlaylistResponse getPlaylistById(String accountId, String playlistId);

    PlaylistResponse updatePlaylist(String accountId, String playlistId, UpdatePlaylistRequest request);

    void deletePlaylist(String accountId, String playlistId);

}
