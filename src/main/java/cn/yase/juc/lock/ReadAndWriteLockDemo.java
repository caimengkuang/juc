package cn.yase.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁（写锁）:
 * 指该锁一次只能被一个线程所持有。对ReentrantLock和Synchronized而言都是独占锁
 * <p>
 * 共享锁（读锁）:
 * 指该锁可被多个线程所持有
 * <p>
 * 对 ReentrantReadWriterLock 其读锁是共享锁，其写锁是独占锁。
 * <p>
 * 读锁对共享锁可保证并发读是非常高效的，读写、写读、写写过程是互斥的。
 * <p>
 * 写操作:原子+独占，整个过程必须是一个完整的过程。
 *
 * @author yase
 * @data 2019/10/13
 */
public class ReadAndWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock rwLock =  new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入");
            TimeUnit.SECONDS.sleep(1);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写完");
        }catch (InterruptedException e) {

        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key) {

        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + "\t读取" + "\t" + map.get(key));
        }catch (InterruptedException e){

        }finally {
            rwLock.readLock().unlock();
        }

    }
}