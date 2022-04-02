package com.platform.comservice.service;

import com.platform.common.util.ProxyUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理类
 * @author Advance
 * @date 2022年03月04日 11:01
 * @since V1.0.0
 */
public class AbstractProxyService {

    static class CGLibInterceptor implements MethodInterceptor{
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o,objects);
        }
    }

    static class RealService<T> implements ProxyUtil.IService {
        void doAction(){
            System.out.println("被代理方法");
        }

        @Override
        public void doInvoke() {
            System.out.println("被代理方法");
        }
    }

    static class StringClass<T> extends RealService<String> {
        void doAdd(){
            System.out.println("++++");
        }

        @Override
        void doAction() {
            System.out.println("被代理方法二");
        }
    }

    public static <T> T getService(Class<T> cls){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new CGLibInterceptor());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        RealService<String> service = getService(RealService.class);
        service.doAction();
        //StringClass stringClass = (StringClass) service;
        //stringClass.doAction();
        StringClass strCls = getService(StringClass.class);
        strCls.doAction();
        ///=============================第二种代理方式======================
        RealService relService = new RealService();
        ProxyUtil.IService proxy = ProxyUtil.ProxyDynamicHandler.getProxy(relService.getClass(), new ProxyUtil.ProxyDynamicHandler(relService));
        proxy.doInvoke();
    }

}

