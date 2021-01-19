package com.junyi.basis.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * User: JY
 * Date: 2020/7/18 0018
 * Description: JDK动态代理示例
 */
@Slf4j
public class JdkDynamicProxyDemo {
    public static void main(String[] args) {
        Hello hello = (Hello) ProxyFactory.getProxy(new HelloImpl());
        log.info(hello.morning("Apple"));

    }

    static class ProxyFactory{
        public static Object getProxy(Object object) {
            return Proxy.newProxyInstance(
                    object.getClass().getClassLoader(), // 传入ClassLoader
                    object.getClass().getInterfaces(), // 传入要实现的接口
                    new MyInvocationHandler(object)); // 传入处理调用方法的InvocationHandler
        }
    }

    interface Hello {
        String morning(String name);
    }

    static class HelloImpl implements Hello {
        @Override
        public String morning(String name) {
            return "Hello " + name;
        }
    }

    static class MyInvocationHandler implements InvocationHandler {

        private Object target;
        public MyInvocationHandler(Object object) {
            this.target = object;
        }

        /**
         * @param proxy:被代理的对象
         * @param method
         * @param args
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method);
            Object result = method.invoke(target, args);
            if (method.getName().equals("morning")) {
                System.out.println("Good morning, " + args[0]);
            }
            return result;
        }
    }
}



