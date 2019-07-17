/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.server;

/**
 * @author yangjikang
 * @date 2019/7/17 15:39
 * @modified By yangjikang
 */
public class HelloRPCImpl implements HelloRPC {

    @Override
    public String hello(String name) {
        return "hello,"+name;
    }
}
