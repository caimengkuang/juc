package cn.yase.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ## 什么是ABA问题
 *     有两个线程都从主内存中获取到同一个共享变量，线程A修改将副本值由1改为来2，还没有执行比较交换时，
 *     线程B已经将值变成了3，并且又将3改为了1。此时线程A拿预期值1和内存中的值(1)进行比较，发现相等，线程A操作成功
 * ## 解决方案:
 *      原子引用 AtomicReference
 *      带版本戳的原子应用
 *
 * @author yase
 * @since 2019/10/11 下午10:14
 */
public class ABADemo {

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);


    public static void main(String[] args) throws InterruptedException {

        System.out.println("================ABA问题的产生====================");
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(()->{
            // 暂停1秒，保证上面的t1线程完成一个ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());

        },"t2").start();

        System.out.println("================ABA问题的解决====================");

        new Thread(()->{
            // 获取版本号
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号"+atomicStampedReference.getStamp());
            // 暂停1秒钟t3线程
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第2次版本号"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第3次版本号"+atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(()->{

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 暂停1秒钟t3线程
            atomicStampedReference.compareAndSet(100,9999,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号"+atomicStampedReference.getStamp());
        },"t4").start();


    }

}


