/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.fourdemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 服务端
 *
 * @author yangjikang
 * @date 2019/6/28 16:39
 * @modified By yangjikang
 * @since 1.0.0
 */
public class MyChatClient {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer());

            Channel channel = bootstrap.connect("localhost",8899).sync().channel();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                channel.writeAndFlush(br.readLine()+"\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
