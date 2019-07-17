/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.fourdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author yangjikang
 * @date 2019/6/28 16:43
 * @modified By yangjikang
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel>{

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
