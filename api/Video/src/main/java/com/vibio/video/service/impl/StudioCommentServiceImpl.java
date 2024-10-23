/*
 *  StudioCommentServiceImpl
 *  @author: Minhhieuano
 *  @created 10/16/2024 10:51 AM
 * */


package com.vibio.video.service.impl;

import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.dto.response.PageableResponse;
import com.vibio.video.service.CommentService;
import com.vibio.video.service.StudioCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioCommentServiceImpl implements StudioCommentService {

    private final CommentService commentService;

    @Override
    public PageableResponse<CommentResponse> getComments(
            String videoId,
            String accountId,
            String parentId,
            int page,
            int limit
    ) {
        return commentService.getAllComment(videoId, accountId, parentId, page, limit);
    }
}
