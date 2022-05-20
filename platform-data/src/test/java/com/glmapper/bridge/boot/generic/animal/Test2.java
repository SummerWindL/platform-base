package com.glmapper.bridge.boot.generic.animal;

/**
 * @author Advance
 * @date 2022年04月03日 14:28
 * @since V1.0.0
 */
public class Test2 {
//    public Class<T> classT;
    public Class<?> clazz;
    public static <T> T createInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        A a = createInstance(A.class);
        B b = createInstance(B.class);
    }
    /**
     * class<T> 在实例化的时候，T要替换成具体类。class<?> 它是个通配泛型，
     * ？可以代表任何类型，所以主要用于声明时的限制情况。比如，我们可以这样做声明
     * public Class<T> classT; 不可以 因为 T 需要指定类型
     * public Class<?> clazz;  可以
     *
     * 所以当不知道定声明什么类型的Class的时候可以定义一个Class<?>.
     *
     */
}
class A{}
class B{}

class Test3<T> {
    public Class<?> clazz;
    //不会报错
    public Class<T> clazzT;
}
