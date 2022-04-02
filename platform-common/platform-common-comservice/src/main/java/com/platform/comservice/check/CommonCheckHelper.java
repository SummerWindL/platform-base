package com.platform.comservice.check;

import com.platform.comservice.callback.CommonCheckCallBack;
import com.platform.comservice.callback.RegistCallback;
import com.platform.comservice.service.AbstractProxyService;
import com.platform.core.base.entity.CommonCheckResult;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 业务校验逻辑
 * @author Advance
 * @date 2022年03月09日 9:10
 * @since V1.0.0
 */
public abstract class CommonCheckHelper extends RegistCallback {

    /**
     * 通用业务校验方法
     * @author Advance
     * @date 2022/3/9 9:16
     * @return com.platform.core.base.entity.CommonCheckResult
     */
//    CommonCheckResult checkBussinessFunc();

    /**
     * 通用业务校验方法
     * @author Advance
     * @date 2022/3/9 10:11
     * @param cls
     * @param params
     * @return T
     */
    public static <T> T checkBussinessFunc(Class<T> cls, Map<String,Object> params){
        CommonCheckResult result = new CommonCheckResult();
        //1、通过反射获取到传入的类
        //T service = AbstractProxyService.getService(cls);

        reInvoke(cls,"check",new Object[]{params});

        return (T) result;
    }

    public static <T> T reInvoke(Class<T> cls,String methodName,Object[] params){
        Method method = ReflectionUtils.findMethod(cls, methodName, Map.class);
        Object[] requestParams = new Object[1];
        requestParams[0]=params[0];
        try {
            ReflectionUtils.invokeMethod(method, cls.newInstance(), requestParams);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) new Object();
    }

    /**
     * 通用业务校验方法 (不带请求参数)
     * @author Advance
     * @date 2022/3/9 10:11
     * @param cls
     * @param params
     * @return T
     */
    public static <T> T checkBussinessFunc(Class<T> cls){
        CommonCheckResult result = new CommonCheckResult();
        //1、通过反射获取到传入的类
        T service = AbstractProxyService.getService(cls);
        Method check = ReflectionUtils.findMethod(cls, "check");
        //2、执行传入类的方法进行业务校验
        try {
            try {
                Object invoke = check.invoke(cls.newInstance());//调用校验方法
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    @Override
    public void callback(CommonCheckCallBack callBack) {
        callBack.slove();
    }
}
