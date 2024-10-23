package com.vibio.stream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class StreamingController {

    private static final String CLOUDINARY_VIDEO_URL = "https://res.cloudinary.com/dymmvrufy/video/authenticated/s--HR7oH8hs--/v1728315099/vibio/videos/waJ8AokX26eh2Fj61kRmf.mp4";  // Replace with actual URL
    private final WebClient webClient = WebClient.create();

    @GetMapping(value = "/stream-video", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<Flux<DataBuffer>>> streamVideo(
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) {

        WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get()
                .uri(CLOUDINARY_VIDEO_URL)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes");

        if (range != null && !range.isEmpty()) {
            requestHeadersSpec = requestHeadersSpec.header(HttpHeaders.RANGE, range);
        }

        Flux<DataBuffer> videoStream = requestHeadersSpec.retrieve().bodyToFlux(DataBuffer.class);

        return Mono.just(
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .body(videoStream)
        );
    }


}