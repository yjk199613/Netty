/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.seconddemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author yangjikang
 * @date 2019/7/5 14:12
 * @modified By yangjikang
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress());
        System.out.println("client output"+ s);
        channelHandlerContext.writeAndFlush("from Client"+ new Date());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自云");
    }
}
