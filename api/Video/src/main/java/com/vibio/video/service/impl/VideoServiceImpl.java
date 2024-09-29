/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:22 PM
 * */

package com.vibio.video.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.video.client.ChannelClient;
import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.dto.response.ChannelResponse;
import com.vibio.video.dto.response.ResourceUploaderResponse;
import com.vibio.video.dto.response.UploadVideoResponse;
import com.vibio.video.entity.Video;
import com.vibio.video.event.eventModel.UploadVideoEvent;
import com.vibio.video.event.producer.VideoEventProducer;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.mapper.VideoMapper;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.ResourceService;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
	private final VideoRepository videoRepository;
	private final ChannelClient channelClient;
	private final VideoEventProducer videoEventProducer;
	private final VideoMapper videoMapper;
	private final ResourceService resourceService;

	@SneakyThrows
	@Override
	public UploadVideoResponse uploadVideo(MultipartFile file, String accountId) {
		ApiResponse<ChannelResponse> channelResponse = channelClient.getChannelInfoByAccountId(accountId);
		ChannelResponse channel = channelResponse.getData();

		Video video = videoRepository.save(Video.builder()
				.channelId(channel.getId())
				.title(NanoIdUtils.randomNanoId())
				.description("No-desc")
				.build());

		videoEventProducer.uploadVideo(UploadVideoEvent.builder()
				.channelId(channel.getId())
				.videoId(video.getId())
				.video(file.getInputStream().readAllBytes())
				.build());

		return videoMapper.videoToUploadVideoResponse(video);
	}

	@Override
	@Async
	public void uploadVideoAsync(String videoId, String channelId, byte[] file) {
		Video video = videoRepository
				.findById(videoId)
				.orElseThrow(() -> new NotfoundException("Video with id " + videoId + " not found"));
		try {
			ResourceUploaderResponse uploadResponse = resourceService.uploadVideo(file, videoId, "/");
			video.setUploadStatus(UploadStatus.SUCCESS);
			video.setUrl(uploadResponse.getUrl());
			video.setAllowedComment(true);
			// TODO: send notify video upload success to user (userId)
		} catch (Exception ex) {
			video.setUploadStatus(UploadStatus.FAILED);
			// TODO: send notify video upload failed to user (userId)
		}
		videoRepository.save(video);
	}
}
