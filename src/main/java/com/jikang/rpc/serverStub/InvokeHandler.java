/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.serverStub;

import com.jikang.rpc.entity.ClassInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

/**
 *
 * 服务器端业务处理类
 *
 * @author yangjikang
 * @date 2019/7/17 15:40
 * @modified By yangjikang
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {


    /**
     * 得到某个接口下某个实现类的名字
     * @param classInfo
     * @return
     * @throws Exception
     */
    private String getImplClassName(ClassInfo classInfo) throws Exception {
        //服务方接口和实现类所在包路径
        String interfacePath = "com.jikang.rpc.server";
        int lastDot = classInfo.getClassName().lastIndexOf(".");
        String interfaceName = classInfo.getClassName().substring(lastDot);
        Class superaClass = Class.forName(interfacePath + interfaceName);
        //得到某接口下所有实现类
        Reflections reflections = new Reflections(interfacePath);
        Set<Class> implClassSet = reflections.getSubTypesOf(superaClass);
        if (implClassSet.size() == 0) {
            System.out.println("未找到实现类");
            return null;
        }else if (implClassSet.size()>1){
            System.out.println("找到多个实现类，未明确使用哪一个");
            return null;
        }else{
            //把集合转化成数组
            Class[] classes = implClassSet.toArray(new Class[0]);
            return classes[0].getName();//得到实现类的名称
        }

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo) msg;
        Object object = Class.forName(getImplClassName(classInfo)).newInstance();
        Method method = object.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());
        //通过反射调用实现类的方法
        Object result = method.invoke(object, classInfo.getObjects());
        ctx.writeAndFlush(result);
    }
}
