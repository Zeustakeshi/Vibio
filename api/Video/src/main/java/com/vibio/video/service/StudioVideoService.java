/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:22 PM
 * */

package com.vibio.video.service;

import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.dto.request.UpdateVideoRequest;
import com.vibio.video.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

public interface StudioVideoService {

	// Create
	UploadVideoResponse uploadVideo(MultipartFile file, String accountId);

	void uploadVideoAsync(String videoId, ChannelResponse channel, String accountId, byte[] file);

	void uploadThumbnailAsync(String videoId, String channelId, String accountId, byte[] thumbnail);

	// Read
	PageableResponse<StudioVideoResponse> getChannelVideos(String accountId, int page, int limit);

	VideoDetailResponse getVideoDetail(String videoId, String accountId);

	// Update
	VideoDetailResponse updateVideo(String videoId, String accountId, UpdateVideoRequest request);

	UploadStatus updateThumbnail(String videoId, String accountId, MultipartFile thumbnail);

	// Delete

}
