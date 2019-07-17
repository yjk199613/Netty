/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.firstdemo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务器
 *
 * @author yangjikang
 * @date 2019/6/28 14:08
 * @modified By yangjikang
 */
public class TestServer {

    public static void main(String[] args) {
        //定义两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();//接收链接，不做处理
        EventLoopGroup workGroup = new NioEventLoopGroup();//处理链接
        try {
            //ServerBootstrap是用于启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());//子处理器,自己编写一个处理器,用于处理请求
            ChannelFuture sync = serverBootstrap.bind(8899).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
