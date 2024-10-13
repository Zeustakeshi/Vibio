/*
 *  CommentService
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:59 AM
 * */

package com.vibio.video.service;

import com.vibio.video.dto.request.CommentRequest;
import com.vibio.video.dto.request.UpdateCommentRequest;
import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.dto.response.PageableResponse;

public interface CommentService {
    CommentResponse crateComment(String videoId, String accountId, CommentRequest request);

    PageableResponse<CommentResponse> getAllComment(
            String videoId, String accountId, String parentId, int page, int limit);

    PageableResponse<CommentResponse> getAllGuestComment(String videoId, String parentId, int page, int limit);

    CommentResponse updateComment(String videoId, String accountId, String commentId, UpdateCommentRequest request);

    boolean deleteComment(String videoId, String accountId, String commentId);

    void updateReplyCount(String commentParentId, int count, boolean isIncrease);

}
