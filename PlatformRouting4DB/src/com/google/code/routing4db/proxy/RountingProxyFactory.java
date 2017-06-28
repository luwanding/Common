package com.google.code.routing4db.proxy;

import java.lang.reflect.Proxy;

import com.google.code.routing4db.strategy.RoutingStrategy;

/**
 * ����ʵ�ʶ���Ĵ������ش������
 * */
public abstract class RountingProxyFactory {
	
    /***
     * ���ض����·�ɴ������
     * */
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object target, Class<T> interfaceClass, RoutingStrategy routingStrategy) {
		
		if(target == null ||  target.getClass().getInterfaces() == null ||  target.getClass().getInterfaces().length == 0 || routingStrategy == null){
			throw new IllegalArgumentException("arugments(target, interfaceClass, routingStrategy) must not be null");
		}
		interfaceClass = (Class<T>) target.getClass().getInterfaces()[0];
		RoutingInvocationHanlder handler = new RoutingInvocationHanlder(target, interfaceClass, routingStrategy);
		return (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[]{interfaceClass}, handler);
	}
	
}
