/*
 *  StudioCommentService
 *  @author: Minhhieuano
 *  @created 10/16/2024 10:47 AM
 * */

package com.vibio.video.service;

import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.dto.response.PageableResponse;

public interface StudioCommentService {
    PageableResponse<CommentResponse> getComments(
            String videoId, String accountId, String parentId, int page, int limit);


}
