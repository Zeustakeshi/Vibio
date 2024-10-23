/*
 *  StudioPlaylistController
 *  @author: Minhhieuano
 *  @created 10/20/2024 12:13 AM
 * */


package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.request.PlaylistRequest;
import com.vibio.channel.dto.request.UpdatePlaylistRequest;
import com.vibio.channel.dto.request.UpdatePlaylistVideoOrderRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.StudioPlaylistService;
import com.vibio.channel.service.StudioPlaylistVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studio/playlists")
@RequiredArgsConstructor
public class StudioPlaylistController {

    private final StudioPlaylistService playlistService;
    private final StudioPlaylistVideoService playlistVideoService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllPlaylistByChannelId(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(playlistService.getAllByAccountId(user.getId(), page, limit));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> createPlaylist(
            @RequestBody @Valid PlaylistRequest request,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(playlistService.createPlaylist(user.getId(), request));
    }

    @GetMapping("{playlistId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getPlaylistById(
            @PathVariable("playlistId") String playlistId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(playlistService.getPlaylistById(user.getId(), playlistId));
    }

    @PutMapping("{playlistId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updatePlaylist(
            @PathVariable("playlistId") String playlistId,
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestBody @Valid UpdatePlaylistRequest request) {
        return ApiResponse.success(playlistService.updatePlaylist(user.getId(), playlistId, request));
    }

    @DeleteMapping("{playlistId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> deletePlaylist(
            @PathVariable("playlistId") String playlistId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        playlistService.deletePlaylist(user.getId(), playlistId);
        return ApiResponse.success(true);
    }

    @GetMapping("{playlistId}/videos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllPlaylistVideo(
            @PathVariable("playlistId") String playlistId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(playlistVideoService.getAllPlaylistVideo(user.getId(), playlistId, page, limit));
    }

    @PostMapping("{playlistId}/videos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> addVideoToPlaylist(
            @PathVariable("playlistId") String playlistId,
            @RequestParam("videoId") String videoId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(playlistVideoService.addVideoToPlaylist(user.getId(), playlistId, videoId));
    }

    @PatchMapping("{playlistId}/videos/order")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updateVideoOrder(
            @PathVariable("playlistId") String playlistId,
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestBody @Valid UpdatePlaylistVideoOrderRequest request
    ) {
        return ApiResponse.success(
                playlistVideoService.updateVideoOrder(user.getId(), playlistId, request)
        );
    }

    @DeleteMapping("{playlistId}/videos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> deleteVideoPlaylist(
            @PathVariable("playlistId") String playlistId,
            @RequestParam("videoId") String videoId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        playlistVideoService.deleteVideoPlaylist(user.getId(), playlistId, videoId);
        return ApiResponse.success(true);
    }


}
