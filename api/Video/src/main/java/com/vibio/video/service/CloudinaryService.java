/*
 *  CloudinaryService
 *  @author: Minhhieuano
 *  @created 10/22/2024 11:27 PM
 * */


package com.vibio.video.service;

public interface CloudinaryService {
    String generateSignedVideoUrl(String publicId, long expirationTimeInSeconds);
}
