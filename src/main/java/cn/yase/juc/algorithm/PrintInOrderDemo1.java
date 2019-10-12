package cn.yase.juc.algorithm;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 三个不同的线程将会共用一个 Foo 实例。
 *
 * 线程 A 将会调用 one() 方法 线程 B 将会调用 two() 方法 线程 C 将会调用 three() 方法 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two()
 * 方法之后被执行。
 *
 * 解决方案:
 *      解法1 : 使用 CountDownLatch
 *      解法2 : 使用 volatile + 自旋
 *      解法3 : 使用 ReentrantLock + Condition
 *      解法4 : 使用信号量 SemaphoreDemo
 *
 * @author yase
 * @since 2019/10/12 上午10:43
 */
public class PrintInOrderDemo1 {

    private CountDownLatch second = new CountDownLatch(1);
    private CountDownLatch third = new CountDownLatch(1);

    public void one() {
        System.out.println(Thread.currentThread().getName()+"\tone");
        second.countDown();
    }

    public void two() throws InterruptedException {
        second.await();
        System.out.println(Thread.currentThread().getName()+"\ttwo");
        third.countDown();
    }

    public void three() throws InterruptedException {
        third.await();
        System.out.println(Thread.currentThread().getName()+"\tthree");
    }

    public static void main(String[] args) {
        PrintInOrderDemo1 printInOrder = new PrintInOrderDemo1();

        new Thread(()->{
            printInOrder.one();
        },"A").start();

        new Thread(()->{
            try {
                printInOrder.two();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                printInOrder.three();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();

    }

}
