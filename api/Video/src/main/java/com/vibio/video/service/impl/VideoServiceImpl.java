/*
 *  VideoServiceImpl
 *  @author: Minhhieuano
 *  @created 10/7/2024 6:20 PM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.client.ChannelClient;
import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.dto.request.FindChannelByIdsRequest;
import com.vibio.video.dto.response.*;
import com.vibio.video.entity.Video;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.mapper.PageMapper;
import com.vibio.video.mapper.VideoMapper;
import com.vibio.video.repository.CommentRepository;
import com.vibio.video.repository.VideoReactionRepository;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.VideoReactionService;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final PageMapper pageMapper;
    private final VideoMapper videoMapper;
    private final ChannelClient channelClient;
    private final VideoReactionRepository videoReactionRepository;
    private final CommentRepository commentRepository;
    private final VideoReactionService videoReactionService;

    @Override
    public PageableResponse<VideoResponse> getFeeds(String accountId, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Video> videos = videoRepository.findAllPublicVideo(pageRequest);

        ApiResponse<List<ChannelResponse>> channelResponses =
                channelClient.findChannelByIdInIds(FindChannelByIdsRequest.builder()
                        .ids(videos.map(Video::getChannelId).stream().toList())
                        .build());

        List<ChannelResponse> channels = channelResponses.getData();

        return pageMapper.toPageableResponse(videos.map(v -> {
            VideoResponse videoResponse = videoMapper.videoToVideoResponse(v);
            ChannelResponse channel = channels.stream()
                    .filter(c -> c.getId().equals(v.getChannelId()))
                    .findFirst()
                    .orElse(ChannelResponse.builder()
                            .id("not-found")
                            .thumbnail("")
                            .name("not-found")
                            .build());
            videoResponse.setChannel(channel);
            return videoResponse;
        }));
    }

    @Override
    public PageableResponse<VideoResponse> getGuestFeeds(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Video> videos = videoRepository.findAllPublicVideo(pageRequest);

        if (videos.isEmpty()) {
            return pageMapper.toPageableResponse(Page.empty());
        }

        ApiResponse<List<ChannelResponse>> channelResponses =
                channelClient.findChannelByIdInIds(FindChannelByIdsRequest.builder()
                        .ids(videos.map(Video::getChannelId).stream().toList())
                        .build());

        List<ChannelResponse> channels = channelResponses.getData();

        return pageMapper.toPageableResponse(videos.map(v -> {
            VideoResponse videoResponse = videoMapper.videoToVideoResponse(v);
            Optional<ChannelResponse> channelInfoOptional = channels.stream()
                    .filter(c -> c.getId().equals(v.getChannelId()))
                    .findFirst();
            channelInfoOptional.ifPresent(videoResponse::setChannel);
            return videoResponse;
        }));
    }

    @Override
    public VideoDetailResponse getVideoById(String videoId, String accountId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new NotfoundException("Video not found"));
        VideoDetailResponse videoResponse = videoMapper.videoToVideoDetailResponse(video);

        videoResponse.setLiked(
                videoReactionRepository.existsByVideoIdAndUserIdAndReactionType(videoId, accountId, ReactionType.LIKE));
        videoResponse.setDisliked(videoReactionRepository.existsByVideoIdAndUserIdAndReactionType(
                videoId, accountId, ReactionType.DISLIKE));

        return videoResponse;
    }

    @Override
    public VideoDetailResponse getVideoByIdGuest(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new NotfoundException("Video not found"));
        return videoMapper.videoToVideoDetailResponse(video);
    }

    @Override
    public void updateCommentCount(String videoId) {
        Video video = videoRepository
                .findById(videoId)
                .orElseThrow(() -> new NotfoundException("Video " + videoId + " not found"));

        video.setCommentCount(commentRepository.countByVideoId(videoId));
        videoRepository.save(video);
    }

    @Override
    public void updateVideoReactionCount(String videoId) {
        Video video = videoRepository
                .findById(videoId)
                .orElseThrow(() -> new NotfoundException("Video " + videoId + " not found"));
        video.setLikeCount(videoReactionRepository.countLikeByVideoId(videoId));
        video.setDislikeCount(videoReactionRepository.countDislikeByVideoId(videoId));
        videoRepository.save(video);
    }
}
