/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.seconddemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author yangjikang
 * @date 2019/7/5 14:12
 * @modified By yangjikang
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress()+","+s);
        channelHandlerContext.channel().writeAndFlush("from NettyServer"+ UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
