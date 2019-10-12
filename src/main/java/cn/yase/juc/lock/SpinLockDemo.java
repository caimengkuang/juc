package cn.yase.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * 自旋锁:
 *      1.什么是自旋锁:
 *          是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁。
 *      2.优点:
 *          减少线程上下文切换
 *      3.缺点:
 *          循环会消耗CPU
 *
 * @author yase
 * @data 2019/10/11
 */
public class SpinLockDemo {

    /**
     * 原子引用线程
     */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\tcome in");

        while (!atomicReference.compareAndSet(null,thread)){

        }

    }

    public void unLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked unLock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();

        },"A").start();



        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        },"B").start();
    }

}
