package com.glmapper.bridge.boot.generic.animal;

import com.glmapper.bridge.boot.generic.GlmapperGeneric;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Advance
 * @date 2022年04月03日 11:21
 * @since V1.0.0
 */
public class Test {
    public static void main(String[] args) {
//        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric<>();
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        System.out.println(glmapperGeneric.countLegs(dogs));
//        System.out.println(glmapperGeneric.countLegs1(dogs));

        System.out.println(test(new Cat(),new Dog()));

        List<Animal> animals = new ArrayList<>();

        test(animals,dogs);


        testNon();
        A();
        MultiBoundTest();
    }

    /**
     * 上界通配符 <? extends E>
     *   上界：用extends关键字声明，表示参数化的类型可能是所指定的类型，或者
     *   是此类型的子类
     *
     *   在类型参数中使用 extends 表示这个泛型中的参数必须是E或者E的子类，这样有两个好处
     *
     *   -> 如果传入的类不是E或者E的子类，编译不成功
     *   -> 泛型中可以使用E的方法，要不然还得强转成E才能使用
     *
     *   类型参数列表中如果有多个类型参数上限，用逗号分分隔开
     * @author Advance
     * @date 2022/4/3 11:28
     * @param arg1
     * @param arg2
     * @return E
     */
    private static <K extends Animal,E extends Dog> E test(K arg1, E arg2){
        E result = arg2;
        arg2.compareTo(arg1);
        return result;
    }

    /**
     * 下界通配符 <? super E>
     *
     *     下界：用super进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至Object
     *
     *     此处示例，dst 类型 大于等于 src的类型 即 装得下dst的容器也能装下src
     * @author Advance
     * @date 2022/4/3 12:21
     * @param dst
     * @param src
     */
    private static <T> void test(List<? super T> dst, List<T> src){
        for(T t:src){
            dst.add(t);
        }
    }

    /**
     * ？和 T 的区别
     * T 是一个确定的类型，通常用于泛型类和泛型方法的定义，？是一个 不确定 的类型，通常用于泛型方法的调用代码合形象
     * 不能用于定义类合泛型方法
     *
     */

    ////////////////////////////////区别一：通过T来确保泛型参数的一致性//////////////////////////////
    //通过T 来确保泛型参数的一致性
    public <T extends Number> void test1(List<T> dest,List<T> src){

    }

    public void doT(){
        List<Integer> list = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        test1(list,list); //确保一致性 要么是Integer 要么是Long
    }


    //通配符是 不确定的，所以这个方法不能保证两个List 具有相同的元素类型
    public void test2(List<? extends Number> dest ,List<? extends Number> src){

    }

    public void doCom(){
        List<Integer> list = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        test2(list,list2); //不能保证两个类型是一致的
    }

    public static void testNon(){
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric<>();
        List<String> dest = new ArrayList<>();
        dest.add("1");
        List<Long> src = new ArrayList<>();
        src.add(1L);
        glmapperGeneric.testNon(dest,src);
    }

    //////////////////////////////区别二 、类型参数可以多冲限定而通配符不行////////////////////////

    static MultiLimit A(){
     return new MultiLimit();
    }

    public static void MultiBoundTest(){
        MultiLimitBoundImpl multiLimit = new MultiLimitBoundImpl();
        A().test(multiLimit);
    }

    ///////////////////////////区别三、通配符可以使用超类限定而类型参数不行/////////////////////////
    /**
     * 简而言之
     * T extends A
     *
     * ? extends A
     * ? super A
     */

}

class MultiLimit implements MultiLimitInterfaceA,MultiLimitInterfaceB{
    /**
     * 使用 “&” 符号 设定多重边界（Multi Bounds）
     *
     * 使用 & 符号设定多重边界，指定泛型类型T必须是MultiLimitInterfaceA和MultiLimitInterfaceB的共有子类型，
     * 此时变量T就具有了所有限定的方法和属性，对于通配符来说，因为它不是一个确定的类型，所以不能进行多重限定
     * @author Advance
     * @date 2022/4/3 14:05
     * @param t
     */
    public static <T extends MultiLimitInterfaceA & MultiLimitInterfaceB> void test(T t){
        System.out.println(t.getClass().getSimpleName());
    }
    //使用
}

interface MultiLimitInterfaceA{
    public static final String ALL="ALL";

}
interface MultiLimitInterfaceB{
    public static final String ALL="ALL";
}
