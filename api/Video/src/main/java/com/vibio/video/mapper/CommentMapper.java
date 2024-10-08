/*
 *  CommentMapper
 *  @author: Minhhieuano
 *  @created 10/6/2024 9:19 AM
 * */

package com.vibio.video.mapper;

import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "likeCount", ignore = true)
	@Mapping(target = "replyCount", ignore = true)
	@Mapping(target = "owner", ignore = true)
	CommentResponse commentToCommentResponse(Comment comment);
}
