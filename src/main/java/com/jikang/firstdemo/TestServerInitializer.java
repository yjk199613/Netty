/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.firstdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * 初始化器
 *
 * @author yangjikang
 * @date 2019/6/28 14:21
 * @modified By yangjikang
 * @since 1.0.0
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("httpServerCodec",new HttpServerCodec());
        channelPipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
