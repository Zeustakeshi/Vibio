/*
 *  PlaylistController
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:27 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.PlaylistService;
import com.vibio.channel.service.PlaylistVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class PlaylistController {
	private final PlaylistService playlistService;
	private final PlaylistVideoService playlistVideoService;

	@GetMapping("{channelId}/playlists")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllPlaylistByChannelId(
			@PathVariable("channelId") String channelId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
		return ApiResponse.success(playlistService.getAllPlaylistPublicByChannelId(channelId, page, limit));
	}

	@GetMapping("/playlists/{playlistId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getPublicPlaylistById(@PathVariable("playlistId") String playlistId) {
		return ApiResponse.success(playlistService.getPublicPlaylistById(playlistId));
	}

	@GetMapping("/playlists/{playlistId}/videos")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllPlaylistVideo(
			@PathVariable("playlistId") String playlistId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {
		return ApiResponse.success(playlistVideoService.getAllPlaylistVideo(playlistId, page, limit));
	}
}
