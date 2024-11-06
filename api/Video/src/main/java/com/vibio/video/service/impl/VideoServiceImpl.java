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
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final PageMapper pageMapper;
    private final VideoMapper videoMapper;
    private final ChannelClient channelClient;
    private final VideoReactionRepository videoReactionRepository;
    private final CommentRepository commentRepository;

    @Override
    public PageableResponse<VideoResponse> getFeeds(String accountId, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Video> videos = videoRepository.findAllPublicVideo(pageRequest);

        List<ChannelResponse> channels = findChannelByChannelIds(videos.map(Video::getChannelId).toList());

        return pageVideoToPageVideoResponse(videos, channels);
    }

    @Override
    public PageableResponse<VideoResponse> getAllPublicVideoByChannelId(String channelId, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Video> videos = videoRepository.findAllPublicByChannelId(channelId, pageRequest);

        List<ChannelResponse> channels = findChannelByChannelIds(videos.map(Video::getChannelId).toList());

        return pageVideoToPageVideoResponse(videos, channels);
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

        return pageVideoToPageVideoResponse(videos, channels);
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

    private List<ChannelResponse> findChannelByChannelIds(List<String> channelIds) {
        return channelClient.findChannelByIdInIds(FindChannelByIdsRequest.builder()
                .ids(channelIds)
                .build()).getData();
    }

    private PageableResponse<VideoResponse> pageVideoToPageVideoResponse(Page<Video> videos, List<ChannelResponse> channels) {
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

}
