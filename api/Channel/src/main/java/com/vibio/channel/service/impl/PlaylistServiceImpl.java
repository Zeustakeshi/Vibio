/*
 *  PlaylistServiceImpl
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:19 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistResponse;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.mapper.PlaylistMapper;
import com.vibio.channel.model.Playlist;
import com.vibio.channel.repository.PlaylistRepository;
import com.vibio.channel.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

	private final PlaylistRepository playlistRepository;
	private final PlaylistMapper playlistMapper;
	private final PageMapper pageMapper;

	public PlaylistResponse getPublicPlaylistById(String playlistId) {
		Playlist playlist = playlistRepository
				.findPublicById(playlistId)
				.orElseThrow(() -> new NotfoundException(("Playlist " + playlistId + " not found")));

		return playlistMapper.playlistToPlaylistResponse(playlist);
	}

	@Override
	public PageableResponse<PlaylistResponse> getAllPlaylistPublicByChannelId(String channelId, int page, int limit) {
		PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));

		Page<Playlist> playlists = playlistRepository.findAllPublicByChannelId(channelId, request);

		return pageMapper.toPageableResponse(playlists.map(playlistMapper::playlistToPlaylistResponse));
	}
}
