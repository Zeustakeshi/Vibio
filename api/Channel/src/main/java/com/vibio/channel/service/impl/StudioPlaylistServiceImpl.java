/*
 *  StudioPlaylistServiceImpl
 *  @author: Minhhieuano
 *  @created 10/20/2024 12:52 AM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.request.PlaylistRequest;
import com.vibio.channel.dto.request.UpdatePlaylistRequest;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistResponse;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.mapper.PlaylistMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.model.Playlist;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.repository.PlaylistRepository;
import com.vibio.channel.service.ChannelService;
import com.vibio.channel.service.StudioPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioPlaylistServiceImpl implements StudioPlaylistService {

	private final PlaylistRepository playlistRepository;
	private final ChannelRepository channelRepository;
	private final ChannelService channelService;
	private final PageMapper pageMapper;
	private final PlaylistMapper playlistMapper;

	@Value("${assets.default-playlist-thumbnail}")
	private String defaultThumbnail;

	@Override
	public PageableResponse<PlaylistResponse> getAllByAccountId(String accountId, int page, int limit) {

		Channel channel = channelService.findByAccountId(accountId);

		PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));

		Page<Playlist> playlists = playlistRepository.findAllByChannelId(channel.getId(), request);

		return pageMapper.toPageableResponse(playlists.map(playlistMapper::playlistToPlaylistResponse));
	}

	@Override
	public PlaylistResponse createPlaylist(String accountId, PlaylistRequest request) {
		Channel channel = channelService.findByAccountId(accountId);

		if (playlistRepository.existsByNameAndChannelId(request.getName(), channel.getId())) {
			throw new ConflictException("Playlist name already existed.");
		}

		Playlist playlist = Playlist.builder()
				.name(request.getName())
				.description(request.getDescription())
				.visibility(request.getVisibility())
				.defaultThumbnail(defaultThumbnail)
				.channel(channel)
				.build();

		return playlistMapper.playlistToPlaylistResponse(playlistRepository.save(playlist));
	}

	@Override
	public PlaylistResponse getPlaylistById(String accountId, String playlistId) {
		Channel channel = channelService.findByAccountId(accountId);

		Playlist playlist = playlistRepository
				.findByIdAndChannelId(playlistId, channel.getId())
				.orElseThrow(() -> new NotfoundException("Playlist " + playlistId + " not found"));

		return playlistMapper.playlistToPlaylistResponse(playlist);
	}

	@Override
	public PlaylistResponse updatePlaylist(String accountId, String playlistId, UpdatePlaylistRequest request) {

		Channel channel = channelService.findByAccountId(accountId);

		Playlist playlist = playlistRepository
				.findByIdAndChannelId(playlistId, channel.getId())
				.orElseThrow(() -> new NotfoundException("Playlist " + playlistId + " not found"));

		if (!request.getName().equals(playlist.getName())
				&& playlistRepository.existsByNameAndChannelId(request.getName(), channel.getId())) {
			throw new ConflictException("Playlist name already existed.");
		}

		if (request.getName() != null) playlist.setName(request.getName());
		if (request.getDescription() != null) playlist.setDescription(request.getDescription());
		if (request.getVisibility() != null) playlist.setVisibility(request.getVisibility());

		return playlistMapper.playlistToPlaylistResponse(playlistRepository.save(playlist));
	}

	@Override
	public void deletePlaylist(String accountId, String playlistId) {
		Channel channel = channelService.findByAccountId(accountId);

		Playlist playlist = playlistRepository
				.findByIdAndChannelId(playlistId, channel.getId())
				.orElseThrow(() -> new NotfoundException("Playlist " + playlistId + " not found"));

		playlistRepository.delete(playlist);
	}
}
