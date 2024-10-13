/*
 *  CommentMapper
 *  @author: Minhhieuano
 *  @created 10/6/2024 9:19 AM
 * */

package com.vibio.video.mapper;

import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	CommentResponse commentToCommentResponse(Comment comment);
}
