/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:22 PM
 * */

package com.vibio.video.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.video.client.ChannelClient;
import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.common.enums.Visibility;
import com.vibio.video.dto.request.UpdateVideoRequest;
import com.vibio.video.dto.response.*;
import com.vibio.video.entity.Video;
import com.vibio.video.event.eventModel.UpdateMetadataEvent;
import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.event.eventModel.UploadVideoThumbnailEvent;
import com.vibio.video.event.producer.VideoEventProducer;
import com.vibio.video.exception.BadRequestException;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.mapper.PageMapper;
import com.vibio.video.mapper.VideoMapper;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.ResourceService;
import com.vibio.video.service.StudioVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudioVideoServiceImpl implements StudioVideoService {
    private final VideoRepository videoRepository;
    private final ChannelClient channelClient;
    private final VideoEventProducer videoEventProducer;
    private final VideoMapper videoMapper;
    private final PageMapper pageMapper;
    private final ResourceService resourceService;

    @Override
    public PageableResponse<StudioVideoResponse> getChannelVideos(String accountId, int page, int limit) {
        ChannelDetailResponse channel = getChannelByAccountId(accountId);

        PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<Video> videos = videoRepository.findAllByChannelId(channel.getId(), request);

        return pageMapper.toPageableResponse(videos.map(videoMapper::videoToStudioVideoResponse));
    }

    @Override
    @Transactional
    public StudioVideoDetailResponse getVideoDetail(String videoId, String accountId) {
        ChannelDetailResponse channel = getChannelByAccountId(accountId);

        Video video = videoRepository
                .findByIdAndChannelId(videoId, channel.getId())
                .orElseThrow(() -> new NotfoundException("Video not found"));

        return videoMapper.videoToStudioVideoDetailResponse(video);
    }

    @SneakyThrows
    @Override
    public UploadVideoResponse uploadVideo(MultipartFile file, String accountId) {
        ChannelDetailResponse channel = getChannelByAccountId(accountId);

        Video video = videoRepository.save(Video.builder()
                .channelId(channel.getId())
                .title(NanoIdUtils.randomNanoId())
                .ownerId(accountId)
                .description("No-desc")
                .build());

        videoEventProducer.uploadVideo(UploadVideoEvent.builder()
                .channel(channel)
                .videoId(video.getId())
                .video(file.getInputStream().readAllBytes())
                .build());

        return videoMapper.videoToUploadVideoResponse(video);
    }

    @Override
    @Async
    public void uploadVideoAsync(String videoId, ChannelDetailResponse channel, String accountId, byte[] file) {
        Video video = videoRepository
                .findByIdAndChannelId(videoId, channel.getId())
                .orElseThrow(() -> new NotfoundException("Video with id " + videoId + " not found"));
        try {
            ResourceUploaderResponse uploadResponse = resourceService.uploadVideo(file, videoId, "/");
            video.setUploadStatus(UploadStatus.SUCCESS);
            video.setUrl(uploadResponse.getUrl());
            video.setAllowedComment(true);

            video.setTags(Set.of(channel.getName()));

            // TODO: send notify video upload success to user (accountId)
        } catch (Exception ex) {
            log.error(ex.getMessage());
            video.setUploadStatus(UploadStatus.FAILED);
            // TODO: send notify video upload failed to user (accountId)
        }
        Video updatedVideo = videoRepository.save(video);
        videoEventProducer.updateMetadata(UpdateMetadataEvent
                .builder()
                .videoId(videoId)
                .tags(updatedVideo.getTags())
                .channel(ChannelResponse.builder()
                        .name(channel.getName())
                        .thumbnail(channel.getThumbnail())
                        .id(channel.getId())
                        .build())
                .visibility(updatedVideo.getVisibility())
                .thumbnail(updatedVideo.getThumbnail())
                .title(updatedVideo.getTitle())
                .build());
    }

    @Override
    @Async
    public void uploadThumbnailAsync(String videoId, String channelId, String accountId, byte[] thumbnail) {
        Video video = videoRepository
                .findByIdAndChannelId(videoId, channelId)
                .orElseThrow(() -> new NotfoundException("Video with id " + videoId + " not found"));

        try {
            ResourceUploaderResponse uploadResponse = resourceService.uploadImage(thumbnail, videoId, "/thumbnails/");
            video.setThumbnail(uploadResponse.getUrl());

            videoRepository.save(video);

            // TODO: send notify video upload success to user (accountId)

        } catch (Exception ex) {
            log.error(ex.getMessage());
            // TODO: send notify thumbnail upload failed to user (accountId)
        }
    }

    @Override
    @Transactional
    public StudioVideoDetailResponse updateVideo(String videoId, String accountId, UpdateVideoRequest request) {
        ChannelDetailResponse channel = getChannelByAccountId(accountId);

        Video video = videoRepository
                .findByIdAndChannelId(videoId, channel.getId())
                .orElseThrow(() -> new NotfoundException("Video not found"));

        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());

        if (!request.getVisibility().equals(Visibility.PRIVATE) && !canPublicVideo(video)) {
            throw new BadRequestException("This video can't public. Please check video status and try again.");
        }

        video.setVisibility(request.getVisibility());
        video.setAllowedComment(request.isAllowedComment());

        video.getTags().addAll(request.getTags());

        Video updatedVideo = videoRepository.save(video);

        videoEventProducer.updateMetadata(UpdateMetadataEvent
                .builder()
                .videoId(videoId)
                .tags(updatedVideo.getTags())
                .channel(ChannelResponse.builder()
                        .name(channel.getName())
                        .thumbnail(channel.getThumbnail())
                        .id(channel.getId())
                        .build())
                .visibility(updatedVideo.getVisibility())
                .thumbnail(updatedVideo.getThumbnail())
                .title(updatedVideo.getTitle())
                .build());

        return videoMapper.videoToStudioVideoDetailResponse(updatedVideo);
    }

    @Override
    @SneakyThrows
    public UploadStatus updateThumbnail(String videoId, String accountId, MultipartFile thumbnail) {
        ChannelDetailResponse channel = getChannelByAccountId(accountId);

        if (!videoRepository.existsByIdAndChannelId(videoId, channel.getId())) {
            throw new NotfoundException("Video not found");
        }

        UploadVideoThumbnailEvent event = UploadVideoThumbnailEvent.builder()
                .channelId(channel.getId())
                .videoId(videoId)
                .thumbnail(thumbnail.getInputStream().readAllBytes())
                .build();

        videoEventProducer.uploadVideoThumbnail(event);

        return UploadStatus.PENDING;
    }

    private ChannelDetailResponse getChannelByAccountId(String accountId) {
        ApiResponse<ChannelDetailResponse> channelResponse = channelClient.getChannelDetailByAccountId(accountId);
        return channelResponse.getData();
    }

    private boolean canPublicVideo(Video video) {
        return video.getUploadStatus().equals(UploadStatus.SUCCESS);
    }
}
