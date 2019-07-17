/*
 * Copyright (C), 2013-2019, 天津大海云科技有限公司
 */
package com.jikang.rpc.entity;

import java.io.Serializable;

/**
 *
 * 封装类信息
 *
 *
 * @author yangjikang
 * @date 2019/7/17 15:40
 * @modified By yangjikang
 */
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 2989221128581296303L;

    private String className;//类名

    private String methodName;//方法名

    private Class<?>[] types;//参数类型

    private Object[] objects;//参数列表

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public void setTypes(Class<?>[] types) {
        this.types = types;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
