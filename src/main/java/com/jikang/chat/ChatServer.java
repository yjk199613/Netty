/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author yangjikang
 * @date 2019/7/17 9:34
 * @modified By yangjikang
 */
public class ChatServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        System.out.println("----服务器开始配置----");
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());//解码器
                        pipeline.addLast("encoder",new StringEncoder());//编码器
                        pipeline.addLast(new ChatServerHandler());//处理器
                    }
                });
        System.out.println("----服务器配置完成----");
        System.out.println("----服务器开始启动----");
        ChannelFuture cf = serverBootstrap.bind(9999).sync();
        cf.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        System.out.println("==============");
    }
}
