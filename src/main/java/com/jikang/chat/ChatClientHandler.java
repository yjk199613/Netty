/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yangjikang
 * @date 2019/7/17 9:57
 * @modified By yangjikang
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println("[Client]:"+s.trim());
    }
}
