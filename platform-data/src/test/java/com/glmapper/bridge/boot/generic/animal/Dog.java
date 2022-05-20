package com.glmapper.bridge.boot.generic.animal;

import com.glmapper.bridge.boot.generic.animal.Animal;

/**
 * @author Advance
 * @date 2022年04月03日 11:17
 * @since V1.0.0
 */
public class Dog extends Animal {
    public <T> T compareTo(T t){
        System.out.println("使用子类方法");
        System.out.println(t.getClass().getName());
        return t;
    }
}
