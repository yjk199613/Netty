/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author yangjikang
 * @date 2019/7/17 9:57
 * @modified By yangjikang
 */
public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast(new ChatClientHandler());
                    }
                });
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();

        Channel channel = sync.channel();

        System.out.println("========"+channel.localAddress().toString().substring(1)+"===========");

        System.out.println("--------------聊天程序开启------------------");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            channel.writeAndFlush(s+"\r\n");
        }
        group.shutdownGracefully();
    }
}
