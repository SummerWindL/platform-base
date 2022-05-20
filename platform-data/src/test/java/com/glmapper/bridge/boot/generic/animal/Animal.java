package com.glmapper.bridge.boot.generic.animal;

/**
 * @author Advance
 * @date 2022年04月03日 11:14
 * @since V1.0.0
 */
public class Animal extends Object{
    /**
     * 计算动物legs
     * @author Advance
     * @date 2022/4/3 11:14
     * @return int
     */
    public int countLegs(){
        return 4;
    }

    public <T> T compareTo(T t){
        System.out.println("使用父类方法");
        System.out.println(t.getClass().getName());
        return t;
    }
}
