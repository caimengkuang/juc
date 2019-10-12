package cn.yase.juc.util;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch:
 *      当new CountDownLatch(n) n 为0 时，才能执行await()之后的代码
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
