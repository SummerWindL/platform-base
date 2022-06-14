package com.platform.comservice.door;

import com.alibaba.fastjson.JSON;
import com.platform.common.util.JSONUtil;
import com.platform.common.util.ProxyUtil;
import com.platform.common.util.StringUtil;
import com.platform.comservice.config.StarterService;
import com.platform.comservice.door.annotation.DoDoor;
import com.platform.comservice.service.AbstractProxyService;
import com.platform.core.util.SpringContextUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 切面处理类
 * @author Advance
 * @date 2022年04月12日 10:24
 * @since V1.0.0
 */
@Aspect
@Component
public class DoJoinPoint {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    private StarterService starterService = (StarterService) SpringContextUtil.getBean("starterService");

    @Autowired
    private StarterService starterService;

    @Pointcut("@annotation(com.platform.comservice.door.annotation.DoDoor)")
    public void aopPoint(){

    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        DoDoor door = method.getAnnotation(DoDoor.class);

        String keyValue = getFiledValue(door.key(), jp.getArgs());
//        Class<?> value = (Class<?>) getFiledValue(door.value(),jp.getArgs());
        //转换成map
//        JSONUtil.object2Map(value);
//        Object service = AbstractProxyService.getService(value);
//        setFieldValue(value,jp.getArgs());
//        Class proxy = ProxyUtil.ProxyDynamicHandler.getProxy(value.getClass(), new ProxyUtil.ProxyDynamicHandler(value));
//        Field[] fields = value.getFields();
//        for(Field field:fields){
//            Object userId = field.get("userId");
//        }
        logger.info("platform door handler method: {} value: {}",method.getName(),keyValue);
        if(null == keyValue || "".equals(keyValue)){
            return jp.proceed();
        }
        String[] split = starterService.split(",");
        //TODO 获取数据库白名单配置
        //白名单过滤
        for(String str:split){
            if(keyValue.equals(str)){
                return jp.proceed();
            }
        }
        //拦截
        return returnObject(door,method);
    }

    private void setFieldValue(Class cls, Object[] args) {
        try {
            Object object = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for(Field field:fields){
                String fieldName=field.getName();
                for(Object arg:args){
                    if(!StringUtil.equals(arg.getClass().getSimpleName(),"String")) {
                        Field[] declaredFields = arg.getClass().getDeclaredFields();
                        for(Field declaredField : declaredFields){
                            if(!fieldName.equals("log") && fieldName.equalsIgnoreCase(declaredField.getName())){
                                field.setAccessible(true);
                                setFieldValue(object, arg.getClass().getDeclaredField(declaredField.getName()).get(arg).toString(), field);
                            }
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private static void setFieldValue(Object obj, Object value, Field field)
            throws IllegalArgumentException, IllegalAccessException {
        if (value == null) {
            return;
        }
        if(field.getType()==int.class){
            field.set(obj, Integer.valueOf(value.toString()));
        }else if(field.getType()==double.class){
            field.set(obj, Double.valueOf(value.toString()));
        }/*else if(field.getType()==java.sql.Date.class){
            field.set(obj, DateUtil.ConvertUtil2Sql(DateUtil.convertStr2Date2(value.toString())));
        }*/else{
            field.set(obj, value);
        }
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) {
        return jp.getTarget().getClass();
    }

    private Object returnObject(DoDoor doGate,Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if("".equals(returnJson)){
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);

    }

    private String getFiledValue(String filed,Object[] args){
        String filedValue = null;
        for(Object arg:args){
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            }catch (Exception e) {
                if(args.length == 1 ){
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }

    private  <T> T getFiledValue(Class<T> target,Object[] args){
        for(Object arg: args){
            if(!StringUtil.equals(arg.getClass().getSimpleName(),"String")){
                org.springframework.beans.BeanUtils.copyProperties(arg,target);
            }
        }
        return (T) target;
    }

}
