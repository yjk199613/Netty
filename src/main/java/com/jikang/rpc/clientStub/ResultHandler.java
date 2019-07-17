/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.clientStub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * 客户端业务处理类
 *
 * @author yangjikang
 * @date 2019/7/17 15:38
 * @modified By yangjikang
 */
public class ResultHandler extends ChannelInboundHandlerAdapter{

    private Object response;

    public Object getResponse() {
        return response;
    }

    //读取客户端返回的数据（远程调用的结果）
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
        ctx.close();
    }
}
