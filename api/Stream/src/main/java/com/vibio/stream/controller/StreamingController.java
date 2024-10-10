package com.vibio.stream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequiredArgsConstructor
public class StreamingController {

    private static final String CLOUDINARY_VIDEO_URL = "https://res.cloudinary.com/dymmvrufy/video/authenticated/s--HR7oH8hs--/v1728315099/vibio/videos/waJ8AokX26eh2Fj61kRmf.mp4";  // Replace with actual URL
    private final WebClient webClient = WebClient.create();


}