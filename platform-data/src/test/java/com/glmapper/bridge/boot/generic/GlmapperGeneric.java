package com.glmapper.bridge.boot.generic;

import com.glmapper.bridge.boot.generic.animal.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Java泛型（generics）是JDK5中引入的一个新特性，泛型提供了编译时类型安全监测机制，
 * 该机制允许开发者在编译时检测到非法的类型
 *
 * 泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数
 *
 * 在没有泛型的情况下，通过对类型Object 的引用来实现参数的“任意化”，“任意化”带来的缺点
 * 是要做显示的强制类型转换，而这种转换是要求开发者对实际参数类型可以预知的情况下进行的
 * 对于强制类型转换错误的情况，编译器可能不能提示错误，在运行的时候才出现异常，这是本身就是一个安全隐患
 *
 * 那么泛型的好处就是在编译的时候能够检查类型安全，并且所有的强制转换都是自动和隐式的
 *
 *
 * 通常我们在定义泛型类，泛型方法，泛型接口的时候通常会碰到很多不同的通配符，
 * 比如： T,E,K,V等等，这些通配符的意义又是什么
 *
 * 本质上这些都是通配符，没啥区别，只不过编码时的一种约定俗称的东西。比如
 * 上述代码中的T。我们可以换成A-Z之间的任何一个字母都可以，并不会影响程序的正常运行，
 * 但是如果换成其他字母替代T，在可读性上可能会弱一些。
 * <br>通常情况下，T,E,K,V，? 是这样约定的</br>
 *
 * ? 表示不确定是Java类型
 * T(type) 表示具体的一个java类型
 * K V (key value) 分别代表java键值中的key value
 * E(element) 代表element
 *
 * @author Advance
 * @date 2022年04月03日 10:52
 * @since V1.0.0
 */
public class GlmapperGeneric<T> {
    private T t;
    public void set(T t){
        this.t = t;
    }

    public T getT() {
        return t;
    }


    /**
     * 不指定类型
     * @author Advance
     * @date 2022/4/3 10:55
     */
    public void noSpecifyType(){
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.set("test");
        //需要强制类型转换
        String test = (String)glmapperGeneric.getT();
        System.out.println(test);
    }

    /**
     * 指定类型
     * @author Advance
     * @date 2022/4/3 10:58
     */
    public void specifyType(){
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric<>();
        glmapperGeneric.set("test");
        //不需要强制类型转换
        String test = glmapperGeneric.getT();
        System.out.println(test);
    }

    /**
     *
     * ? 无界通配符
     *
     */

    public static int countLegs(List<? extends Animal> animals){
        int retVal = 0;
        for(Animal animal : animals){
            retVal= animal.countLegs();
        }
        return retVal;
    }

    public static int countLegs1(List<Animal> animals){
        int retVal = 0;
        for(Animal animal : animals){
            retVal= animal.countLegs();
        }
        return retVal;
    }

    public void testNon(List<? extends String> dest , List<? extends Number> src){
        System.out.println(dest+" :: "+src);
    }
}
