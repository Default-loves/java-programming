package com.junyi.basis.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * User: JY
 * Date: 2020/7/18 0018
 * Description: CGLib 动态代理示例
 */
@Slf4j
public class CglibDynamicProxyDemo {
    public static void main(String[] args) {
        HelloService service = (HelloService) ProxyFactory.getProxy(HelloService.class);
        log.info(service.send("Apple"));

    }

    static class ProxyFactory{
        public static Object getProxy(Class<?> cls) {
            Enhancer enhancer = new Enhancer();
            enhancer.setClassLoader(cls.getClassLoader());
            enhancer.setSuperclass(cls);
            enhancer.setCallback(new MyMethodInterceptor());
            return enhancer.create();
        }
    }


    static class HelloService{

        public String send(String message) {
            log.info("Send message: {}", message);
            return "Send success";
        }
    }

    static class MyMethodInterceptor implements MethodInterceptor {
        /**
         *
         * @param o：object which was enhance
         * @param method: method which was enhance
         * @param objects:  args used in method
         * @param methodProxy: original method
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            log.info("Before Method: {}", method.getName());
            Object result = methodProxy.invokeSuper(o, objects);
            log.info("After method: {}", method.getName());
            return result;
        }
    }
}



