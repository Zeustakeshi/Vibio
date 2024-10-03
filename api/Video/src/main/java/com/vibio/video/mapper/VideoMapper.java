/*
 *  VideoMapper
 *  @author: Minhhieuano
 *  @created 9/29/2024 11:22 PM
 * */

package com.vibio.video.mapper;

import com.vibio.video.dto.response.StudioVideoResponse;
import com.vibio.video.dto.response.UploadVideoResponse;
import com.vibio.video.dto.response.VideoDetailResponse;
import com.vibio.video.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {

	@Mapping(source = "id", target = "videoId")
	UploadVideoResponse videoToUploadVideoResponse(Video video);

	StudioVideoResponse videoToVideoResponse(Video video);

	VideoDetailResponse videoToVideoDetailResponse(Video video);
}
