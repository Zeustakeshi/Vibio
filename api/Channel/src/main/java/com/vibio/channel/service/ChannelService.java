/*
 *  ChannelService
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:15 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.event.eventModel.NewChannelEvent;

public interface ChannelService {
    void createChannel(NewChannelEvent event);


}
