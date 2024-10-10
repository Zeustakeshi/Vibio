/*
 *  StreamingService
 *  @author: Minhhieuano
 *  @created 10/8/2024 5:28 PM
 * */


package com.vibio.stream.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private static final String FORMAT = "classpath:video.mp4";

    private final ResourceLoader resourceLoader;


    public Mono<Resource> getVideo() {
        return Mono.fromSupplier(() -> resourceLoader.
                getResource(FORMAT));
    }
}
