/*
 *  PlaylistVideoServiceImpl
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:26 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.client.VideoClient;
import com.vibio.channel.dto.request.FindVideosByIdsRequest;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.PlaylistVideoResponse;
import com.vibio.channel.dto.response.VideoResponse;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.mapper.PlaylistMapper;
import com.vibio.channel.model.PlaylistVideo;
import com.vibio.channel.repository.PlaylistVideoRepository;
import com.vibio.channel.service.PlaylistVideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistVideoServiceImpl implements PlaylistVideoService {
	private final PlaylistVideoRepository playlistVideoRepository;
	private final VideoClient videoClient;
	private final PageMapper pageMapper;
	private final PlaylistMapper playlistMapper;

	@Override
	public PageableResponse<PlaylistVideoResponse> getAllPlaylistVideo(String playlistId, int page, int limit) {

		PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "order"));

		Page<PlaylistVideo> playlistVideos = playlistVideoRepository.findAllByPublicPlaylistId(playlistId, request);

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
}
