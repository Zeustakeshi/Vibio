/*
 *  ChannelController
 *  @author: Minhhieuano
 *  @created 9/19/2024 2:51 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;


}
