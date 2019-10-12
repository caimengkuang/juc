package cn.yase.juc.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * SemaphoreDemo:
 *      信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制
 *
 * demo:
 *      类似于6辆车，抢3个车位，每次最多只能停3个车位，只有一辆车走了，后面的车才能停到新的车位上。
 *
 * @author yase
 * @data 2019/10/13
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 模拟3个车位
        Semaphore semaphore = new Semaphore(3);

        for (int i=1;i<=6;i++){
            new Thread(()->{
                try {
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t 离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }


    }

}
