/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 服务器端业务处理类
 *
 * @author yangjikang
 * @date 2019/7/16 16:31
 * @modified By yangjikang
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server:" + ctx);
        BookMessage.Book book = (BookMessage.Book) msg;
        System.out.println("书名叫什么："+book.getName());
    }

    //数据读取完毕事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("再等等吧，现在厂里效益不好！",CharsetUtil.UTF_8));
    }

    //异常发生事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
