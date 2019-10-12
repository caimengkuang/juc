package cn.yase.juc.algorithm;

import java.util.concurrent.Semaphore;

/**
 * 解法4 : 使用信号量 Semaphore
 * 
 * @author yase
 * @since 2019/10/12 上午11:55
 */
public class PrintInOrderDemo4 {

    /** 只能有一个线程访问资源 */
    private Semaphore two = new Semaphore(0);

    private Semaphore third = new Semaphore(0);

    public void one() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "\tone");
        two.release();
    }

    public void two() throws InterruptedException {
        two.acquire();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "\ttwo");
        third.release();
    }

    public void three() throws InterruptedException {

        third.acquire();

        Thread.sleep(1000);

        System.out.println(Thread.currentThread().getName() + "\tthree");
    }

    public static void main(String[] args) {
        PrintInOrderDemo4 printInOrder = new PrintInOrderDemo4();

        new Thread(() -> {
            try {
                printInOrder.one();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                printInOrder.two();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                printInOrder.three();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
    }

}
