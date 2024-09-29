/*
 *  ResourceService
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:36 PM
 * */

package com.vibio.video.service;

import com.vibio.video.dto.response.ResourceUploaderResponse;
import java.io.IOException;

public interface ResourceService {
	ResourceUploaderResponse uploadVideo(byte[] video, String publicId, String path) throws IOException;

	ResourceUploaderResponse uploadImage(byte[] image, String publicId, String path) throws IOException;

	void deleteVideo(String publicId, String path) throws IOException;

	void deleteImage(String publicId, String path) throws IOException;

	String generateSignedUrl(String publicId);
}
