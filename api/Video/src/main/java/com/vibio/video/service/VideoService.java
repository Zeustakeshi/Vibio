/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:22 PM
 * */

package com.vibio.video.service;

import com.vibio.video.dto.response.UploadVideoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

	UploadVideoResponse uploadVideo(MultipartFile file, String accountId);

	void uploadVideoAsync(String videoId, String channelId, byte[] file);
}
