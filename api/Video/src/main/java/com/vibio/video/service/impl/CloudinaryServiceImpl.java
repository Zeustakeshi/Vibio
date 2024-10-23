/*
 *  CloudinaryServiceImpl
 *  @author: Minhhieuano
 *  @created 10/22/2024 11:29 PM
 * */


package com.vibio.video.service.impl;

import com.cloudinary.Cloudinary;
import com.vibio.video.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String generateSignedVideoUrl(String publicId, long expirationTimeInSeconds) {

        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("public_id", "your_video_public_id");
        params.put("resource_type", "video");

        long timestamp = System.currentTimeMillis() / 1000L;
        long expireAt = timestamp + 3600;

        params.put("expire_at", String.valueOf(expireAt));

        String signature = cloudinary.apiSignRequest(params, cloudinary.config.apiSecret);


        return "https://res.cloudinary.com/" + cloudinary.config.cloudName + "/video/upload/"
                + "v" + timestamp + "/" + publicId + ".mp4"
                + "?api_key=" + cloudinary.config.apiKey
                + "&timestamp=" + timestamp
                + "&expire_at=" + expireAt
                + "&signature=" + signature;
    }
}
