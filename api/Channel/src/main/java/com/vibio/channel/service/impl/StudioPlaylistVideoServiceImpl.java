/*
 *  PlaylistVideoServiceImp
 *  @author: Minhhieuano
 *  @created 10/20/2024 1:01 AM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.client.VideoClient;
import com.vibio.channel.dto.request.FindVideosByIdsRequest;
import com.vibio.channel.dto.request.UpdatePlaylistVideoOrderRequest;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistVideoResponse;
import com.vibio.channel.dto.response.VideoResponse;
import com.vibio.channel.exception.BadRequestException;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.mapper.PlaylistMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.model.Playlist;
import com.vibio.channel.model.PlaylistVideo;
import com.vibio.channel.repository.PlaylistRepository;
import com.vibio.channel.repository.PlaylistVideoRepository;
import com.vibio.channel.service.ChannelService;
import com.vibio.channel.service.StudioPlaylistVideoService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioPlaylistVideoServiceImpl implements StudioPlaylistVideoService {

	private final ChannelService channelService;
	private final PlaylistRepository playlistRepository;
	private final PlaylistVideoRepository playlistVideoRepository;
	private final VideoClient videoClient;
	private final PageMapper pageMapper;
	private final PlaylistMapper playlistMapper;

	@Override
	public PageableResponse<PlaylistVideoResponse> getAllPlaylistVideo(
			String accountId, String playlistId, int page, int limit) {
		PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "order"));

		Page<PlaylistVideo> playlistVideos =
				playlistVideoRepository.findByPlaylistIdAndAccountId(playlistId, accountId, request);

		List<String> videoIds =
				playlistVideos.map(PlaylistVideo::getVideoId).stream().toList();

		List<VideoResponse> videoResponses = videoClient
				.findVideosByIds(FindVideosByIdsRequest.builder().ids(videoIds).build())
				.getData();

		return pageMapper.toPageableResponse(playlistVideos.map(playlistVideo -> {
			VideoResponse videoResponse = videoResponses.stream()
					.filter(v -> v.getId().equals(playlistVideo.getVideoId()))
					.findFirst()
					.orElseThrow(() -> new NotfoundException("video " + playlistVideo.getVideoId() + " not found"));
			PlaylistVideoResponse playlistVideoResponse =
					playlistMapper.videoResponseToPlaylistVideoResponse(videoResponse);
			playlistVideoResponse.setOrder(playlistVideo.getOrder());
			return playlistVideoResponse;
		}));
	}

	@Override
	public Integer addVideoToPlaylist(String accountId, String playlistId, String videoId) {

		Channel channel = channelService.findByAccountId(accountId);

		Playlist playlist = playlistRepository
				.findByIdAndChannelId(playlistId, channel.getId())
				.orElseThrow(() -> new NotfoundException("Playlist " + playlistId + " not found."));

		if (playlistVideoRepository.existsByPlaylistIdAndVideoId(playlistId, videoId)) {
			throw new ConflictException("Video already existed in playlist");
		}

		if (!videoClient.introspectVideo(videoId).getData()) {
			throw new BadRequestException("Invalid videoId " + videoId);
		}

		Integer videoCount = playlistVideoRepository.countByPlaylistId(playlistId);
		Integer order = videoCount + 1;

		PlaylistVideo playlistVideo = PlaylistVideo.builder()
				.order(order)
				.playlist(playlist)
				.videoId(videoId)
				.build();

		playlist.setVideoCount(videoCount + 1);

		playlistVideoRepository.save(playlistVideo);
		playlistRepository.save(playlist);
		return order;
	}

	@Override
	@Transactional
	public Integer updateVideoOrder(String accountId, String playlistId, UpdatePlaylistVideoOrderRequest request) {

		channelService.findByAccountId(accountId);

		PlaylistVideo playlistVideo = playlistVideoRepository
				.findByPlaylistIdAndVideoId(playlistId, request.getVideoId())
				.orElseThrow(() -> new NotfoundException("Video not existed in playlist"));

		int start = playlistVideo.getOrder();
		int end = Math.min(request.getNewOrder(), playlistVideoRepository.countByPlaylistId(playlistId));

		if (start == end) return start;

		if (start > end) playlistVideoRepository.increasePlaylistVideoOrderInRange(playlistId, end, start - 1);
		else playlistVideoRepository.decreasePlaylistVideoOrderInRange(playlistId, start + 1, end);

		playlistVideo.setOrder(end);
		playlistVideoRepository.save(playlistVideo);

		return end;
	}

	@Override
	@Transactional
	public void deleteVideoPlaylist(String accountId, String playlistId, String videoId) {
		Channel channel = channelService.findByAccountId(accountId);

		PlaylistVideo playlistVideo = playlistVideoRepository
				.findByPlaylistIdAndVideoId(playlistId, videoId)
				.orElseThrow(() -> new NotfoundException("Video not existed in playlist"));

		Playlist playlist = playlistRepository
				.findByIdAndChannelId(playlistId, channel.getId())
				.orElseThrow(() -> new NotfoundException("Playlist " + playlistId + " not found."));

		Integer videoCount = playlistVideoRepository.countByPlaylistId(playlistId);

		playlist.setVideoCount(Math.max(videoCount - 1, 0));
		playlistVideoRepository.decreasePlaylistVideoOrderInRange(playlistId, playlistVideo.getOrder(), videoCount);
		playlistVideoRepository.delete(playlistVideo);
		playlistRepository.save(playlist);
	}
}
