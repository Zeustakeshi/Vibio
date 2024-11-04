/*
 *  VideoMapper
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:23 PM
 * */

package com.vibio.search.mapper;

import com.vibio.search.dto.response.SearchVideoResponse;
import com.vibio.search.entity.Video;
import com.vibio.search.event.eventModel.UpdateVideoMetadataEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "id", source = "videoId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "keyword", ignore = true)
    Video updateVideoMetadataEventToVideo(UpdateVideoMetadataEvent event);

    SearchVideoResponse videoToSearchVideoResponse(Video video);
}
