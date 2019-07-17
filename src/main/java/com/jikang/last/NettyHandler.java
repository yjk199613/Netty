/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.last;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 客户端业务处理类
 *
 * @author yangjikang
 * @date 2019/7/16 16:31
 * @modified By yangjikang
 */
public class NettyHandler extends ChannelInboundHandlerAdapter{

    //通道就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板什么时候发工资？", CharsetUtil.UTF_8));
    }

    //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead  老板回复了...");
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发来的消息:"+byteBuf.toString(CharsetUtil.UTF_8));
    }
}
