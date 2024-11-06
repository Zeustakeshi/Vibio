/*
 *  GuestVideoController
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:53 PM
 * */

package com.vibio.video.controller;

import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.common.enums.Visibility;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.entity.Video;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.CloudinaryService;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestVideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;
    private final CloudinaryService cloudinaryService;


    @GetMapping("/feeds")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getFeeds(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        return ApiResponse.success(videoService.getGuestFeeds(page, limit));
    }

    @GetMapping("/channel/{channelId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllVideoByChannelId(
            @PathVariable("channelId") String channelId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        return ApiResponse.success(videoService.getAllPublicVideoByChannelId(channelId, page, limit));
    }


    @GetMapping("{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getVideoById(@PathVariable("videoId") String videoId) {
        return ApiResponse.success(videoService.getVideoByIdGuest(videoId));
    }


    ///
    @GetMapping("/test-video")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> createTestVideo() {
        List<Video> videos = new ArrayList<>();

        for (int i = 0; i < 50; ++i) {
            videos.add(Video.builder()
                    .title("video_" + (i + 1))
                    .description("test video -----")
                    .channelId("GuSzItb2xwFOdXZU2Tfqi")
                    .ownerId("Yq4PXydce6VtxzSwBx3JA")
                    .allowedComment(true)
                    .uploadStatus(UploadStatus.SUCCESS)
                    .url(
                            "https://res.cloudinary.com/dymmvrufy/video/upload/v1727843028/vibio/videos/tn235l9gz4hycobxnxbm.mp4")
                    .thumbnail(
                            "https://i.ytimg.com/vi/XUGywpiiwlE/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCYQFiThIyJKl37xfmdDxjk-pCJLA")
                    .visibility(Visibility.PUBLIC)
                    .build());
        }
        videoRepository.saveAll(videos);

        return ApiResponse.success(videos);
    }
}
