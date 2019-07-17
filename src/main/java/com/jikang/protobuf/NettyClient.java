/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author yangjikang
 * @date 2019/7/16 16:30
 * @modified By yangjikang
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1.创建一个线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建客户端启动助手，完成相关配置
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)//3.设置线程组
                .channel(NioSocketChannel.class)//4.设置客户端通道的实现类
                .handler(new ChannelInitializer<SocketChannel>() {//5.创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("endocer",new ProtobufEncoder());//编码器
                        pipeline.addLast(new NettyHandler());//6.往pipeline链中添加自定义的处理器
                    }
                });
        System.out.println("======客户端准备就绪======");

        //启动客户端去连接服务端  connect方法是异步的,sync方法是同步阻塞的
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();

        //8.关闭连接，异步非阻塞
        sync.channel().closeFuture().sync();
    }
}
