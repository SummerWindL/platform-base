package com.platform.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Advance
 * @date 2022年03月04日 11:19
 * @since V1.0.0
 */
public class ProxyUtil {

    /**
     * 静态内部类 创建器
     * @author Advance
     * @date 2022/3/8 16:13
     */
    public static class Builder{

    }

    public static class ProxyDynamicHandler implements InvocationHandler {

        private Object target;

        public ProxyDynamicHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("调用开始");
            Object obj = method.invoke(target);
            System.out.println("调用结束");
            return obj;
        }
        public static <T> T getProxy(Class<T> service ,ProxyDynamicHandler invocationHandler){
            Object o = Proxy.newProxyInstance(service.getClassLoader(),
                    service.getInterfaces(), invocationHandler);
            return (T) o;
        }
    }

    public static interface IService{
        void doInvoke();
    }

    static class RealService implements IService{

        @Override
        public void doInvoke() {
            System.out.println("被代理类");
        }
    }

    public static void main(String[] args) {
        RealService service = new RealService();
        IService proxy = ProxyDynamicHandler.getProxy(service.getClass(), new ProxyDynamicHandler(service));
        proxy.doInvoke();
    }
    /*public static <T> T getTargetClass(Class<T> proxyClass) throws IllegalAccessException, InstantiationException {
        if (Proxy.isProxyClass(proxyClass) || ClassUtils.isCglibProxyClass(proxyClass)) {
            return (T) proxyClass.getSuperclass().newInstance();
        }
        return (T) proxyClass.newInstance();
    }




    static class RealService{
        void doInvoke(){
            System.out.println("被代理类");
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ProxyDynamicHandler handler = new ProxyDynamicHandler(new RealService());
        Object proxy = handler.getProxy();
        System.out.println(proxy.getClass().getSimpleName());

    }*/

}
