/*
 *  ChannelMapper
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:22 PM
 * */

package com.vibio.channel.mapper;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.channel.dto.response.ChannelBasicResponse;
import com.vibio.channel.dto.response.ChannelDetailResponse;
import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.model.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelDetailResponse channelToChannelDetailResponse(Channel channel);

    ChannelBasicResponse channelToChannelBasicResponse(Channel channel);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "defaultAvatar", target = "thumbnail")
    @Mapping(expression = "java(randomChannelName(event))", target = "name")
    @Mapping(source = "defaultEmail", target = "defaultEmail")
    Channel newChannelEventToChannel(NewChannelEvent event);


    @Mapping(target = "isSubscribed", ignore = true)
    @Mapping(target = "isMember", ignore = true)
    ChannelResponse channelToChannelResponse(Channel channel);

    default String randomChannelName(NewChannelEvent event) {
        return event.getName() + '-' + NanoIdUtils.randomNanoId();
    }
}
