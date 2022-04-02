package com.platform.comservice.useful;

import com.platform.comservice.PlatformComServiceApplicationTests;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLoc 测试
 *
 * @author Advance
 * @date 2022年01月17日 14:56
 * @since V1.0.0
 */
public class StampedLockTest extends PlatformComServiceApplicationTests {
    StampedLock lock = new StampedLock();
    Balance b = new Balance(10000);
    Runnable w = () -> {
        long stamp = lock.writeLock();
        b.setAmount(b.getAmount() + 1000);
        System.out.println("Write: " + b.getAmount());
        lock.unlockWrite(stamp);
    };
    Runnable r = () -> {
        long stamp = lock.tryOptimisticRead();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                System.out.println("Read: " + b.getAmount());
            } finally {
                lock.unlockRead(stamp);
            }
        } else {
            System.out.println("Optimistic read fails");
        }
    };

    @Test
    public void executeRun() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executor.submit(w);
            executor.submit(r);
        }
    }
}
