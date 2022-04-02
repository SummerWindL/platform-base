package com.platform.comservice.useful;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * 累加器测试
 *
 * @author Advance
 * @date 2022年01月17日 15:09
 * @since V1.0.0
 */
public class LongAccumulatorTest {

    /**
     * 并发累加器
     *
     * @author Advance
     * @date 2022/1/17 15:13
     */
    @Test
    public void longAccumulator() {
        LongAccumulator balance = new LongAccumulator(Long::sum, 10000L);
        Runnable w = () -> balance.accumulate(1000L);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executor.submit(w);
        }

        executor.shutdown();
        try {
            if (executor.awaitTermination(1000L, TimeUnit.MILLISECONDS))
                System.out.println("Balance: " + balance.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert balance.get() == 60000L;
    }

    /**
     * 数组的二分查找
     *
     * @author Advance
     * @date 2022/1/17 15:13
     */
    @Test
    public void binarySearchOfArrays() {
        int[] t = new int[]{1, 2, 4, 5};
        int x = Arrays.binarySearch(t, 3);

        assert ~x == 2;
        //负数的二进制是以正数的补码表示，对一个数取反+1 就等于补码，所以这里直接取反就等于 Arrays.binarySearch() 不存在时的返回值了。
    }

    /**
     * Bit Set
     *
     * @author Advance
     * @date 2022/1/17 15:16
     */
    @Test
    public void bitSet() {
        BitSet bs1 = new BitSet();
        bs1.set(0);
        bs1.set(2);
        bs1.set(4);
        System.out.println("bs1 : " + bs1);

        BitSet bs2 = new BitSet();
        bs2.set(1);
        bs2.set(2);
        bs2.set(3);
        System.out.println("bs2 : " + bs2);

        bs2.xor(bs1);
        System.out.println("xor: " + bs2);
    }


}
