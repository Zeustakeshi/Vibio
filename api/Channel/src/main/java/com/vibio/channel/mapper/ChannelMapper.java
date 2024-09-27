/*
 *  ChannelMapper
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:22 PM
 * */

package com.vibio.channel.mapper;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.model.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "defaultAvatar", target = "thumbnail")
	@Mapping(expression = "java(randomChannelName(event.getName()))", target = "name", source = "name")
	Channel newChannelEventToChannel(NewChannelEvent event);

	default String randomChannelName(String name) {
		return name + '-' + NanoIdUtils.randomNanoId();
	}
}
