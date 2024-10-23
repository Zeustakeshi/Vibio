/*
 *  PlaylistMapper
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:17 PM
 * */

package com.vibio.channel.mapper;

import com.vibio.channel.dto.response.PlaylistResponse;
import com.vibio.channel.dto.response.PlaylistVideoResponse;
import com.vibio.channel.dto.response.VideoResponse;
import com.vibio.channel.model.Playlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlaylistResponse playlistToPlaylistResponse(Playlist playlist);

    PlaylistVideoResponse videoResponseToPlaylistVideoResponse(VideoResponse videoResponse);

}
