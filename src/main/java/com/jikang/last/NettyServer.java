/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.last;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yangjikang
 * @date 2019/7/16 16:30
 * @modified By yangjikang
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1.创建一个线程组,接收客户端连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建一个线程组,处理客户端请求
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //3.创建服务器启动助手来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)//4.设置两个线程组
                .channel(NioServerSocketChannel.class)//5.使用NioServerSocketChannel作为服务端通道的实现
                .option(ChannelOption.SO_BACKLOG,125)//6.设置线程队列等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)//7.保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//8.创建通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {//9.在pipeline链上末尾添加我们自定义的处理器
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("=======服务器配置准备就绪========");

        ChannelFuture cf = serverBootstrap.bind(9999).sync();//10.绑定端口  bind方法是异步的,sync方法是同步阻塞的

        System.out.println("=======服务器开始启动===========");

        //11.关闭通道
        cf.channel().closeFuture().sync();//异步
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
