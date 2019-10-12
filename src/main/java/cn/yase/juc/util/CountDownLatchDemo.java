package cn.yase.juc.util;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch:
 *      让一些线程阻塞知道另外一些线程完成一系列操作后才能唤醒。
 *
 *      CountDownLatch主要有两个方法，当一个或多个线程调用await
 *      方法时，调用线程会被阻塞。其他线程调用countDown方法会将计
 *      数器键1(调用countDown方法的线程不会被唤醒)，当计数器的值
 *      变为零时，因调用await方法被阻塞的线程会被唤醒，继续执行
 *
 * demo:
 *      等到教室里的6个人都走完了，班长才能锁门。每走一次减一。
 *
 * @author yase
 * @data 2019/10/13
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教师");
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t=====班长最后关门走人");

    }

}
