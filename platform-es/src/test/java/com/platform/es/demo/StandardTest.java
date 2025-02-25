package com.platform.es.demo;

import com.platform.common.util.StringUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月25日 10:50
 */
public class StandardTest {
    @Test
    public void listTest(){
        String[] str = new String[] { "chen", "yang", "hao" };
        List list = Arrays.asList(str);
        //list.add("yangguanbao");
        System.out.println(list);
        str[0] = "change";
        System.out.println(list);
    }
    @Test
    public void forEachTest(){
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String item = iterator.next();
//            if (StringUtil.equals(item,"1")) {
//                iterator.remove();
//            }
//        }
        System.out.println(list);
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(list);
    }

}
