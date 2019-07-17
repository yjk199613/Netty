/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.client;

import com.jikang.rpc.clientStub.NettyRPCProxy;

/**
 * @author yangjikang
 * @date 2019/7/17 15:37
 * @modified By yangjikang
 */
public class TestNettyRPC {

    public static void main(String[] args) {
        //第一次远程调用
        HelloNetty helloNetty = (HelloNetty) NettyRPCProxy.create(HelloNetty.class);
        System.out.println(helloNetty.hello());

        //第二次远程调用
        HelloRPC helloRPC = (HelloRPC) NettyRPCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("RPC"));
    }
}
