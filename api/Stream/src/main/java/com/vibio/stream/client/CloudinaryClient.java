/*
 *  CloudinaryClient
 *  @author: Minhhieuano
 *  @created 10/8/2024 6:37 PM
 * */


package com.vibio.stream.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cloudinaryClient", url = "https://res.cloudinary.com")
public interface CloudinaryClient {
    @GetMapping(value = "/{cloud_name}/video/upload/{video_name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<byte[]> streamVideo(@PathVariable("cloud_name") String cloudName,
                                       @PathVariable("video_name") String videoName);
}