/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.clientStub;

import com.jikang.rpc.entity.ClassInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * 客户端代理类
 *
 * @author yangjikang
 * @date 2019/7/17 15:38
 * @modified By yangjikang
 */
public class NettyRPCProxy {

    //根据接口创建代理对象
    public static  Object create (Class target){

        return Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //封装ClassInfo
                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassName(target.getName());
                classInfo.setMethodName(method.getName());
                classInfo.setObjects(args);
                classInfo.setTypes(method.getParameterTypes());

                //开始用Netty发送数据
                EventLoopGroup group = new NioEventLoopGroup();
                ResultHandler resultHandler = new ResultHandler();
                try{
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    ChannelPipeline pipeline = socketChannel.pipeline();
                                    pipeline.addLast("encoder",new ObjectEncoder());//编码器
                                    pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.cacheDisabled(null)));//解码器
                                    pipeline.addLast(resultHandler);
                                }
                            });
                    ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();
                    channelFuture.channel().writeAndFlush(classInfo).sync();
                    channelFuture.channel().closeFuture().sync();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                        group.shutdownGracefully();
                }
                return resultHandler.getResponse();
            }
        });
    }


}
