/*
 *  InternalVideoServiceImpl
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:52 PM
 * */


package com.vibio.video.service.impl;

import com.vibio.video.dto.request.FindVideosByIdsRequest;
import com.vibio.video.dto.response.VideoResponse;
import com.vibio.video.entity.Video;
import com.vibio.video.mapper.VideoMapper;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.InternalVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalVideoServiceImpl implements InternalVideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public boolean introspectVideo(String videoId) {
        return videoRepository.existsAndPublicById(videoId);
    }

    @Override
    public List<VideoResponse> getAllVideoByIds(FindVideosByIdsRequest request) {
        List<Video> videos = videoRepository.findAllByIdIn(request.getIds());
        return videos.stream().map(videoMapper::videoToVideoResponse).toList();
    }

}
