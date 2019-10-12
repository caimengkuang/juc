package cn.yase.juc.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier:
 *      让一组线程到达一个屏障(也可以叫做同步点)时被阻塞，
 *      直到最后一个线程到达屏障时，屏障才会开门，所有被
 *      屏障拦截的线程才会继续干活，线程进入屏障通过CyclicBarrier
 *      的await()方法。
 *
 * demo:
 *      集齐7颗龙珠时，才能召唤神龙
 *
 * @author yase
 * @data 2019/10/13
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()-> System.out.println("召唤神龙"));

        for (int i=1;i<=7;i++){
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+"\t1颗龙珠集齐");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }



    }

}
