package cn.yase.juc.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解法3 : 使用 ReentrantLock + Condition
 *
 * @author yase
 * @since 2019/10/12 上午11:08
 */
public class PrintInOrderDemo3 {

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    private volatile int flag = 1;

    public void one() {

        lock.lock();
        try {
            while (flag != 1){
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"\tone");
            flag = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void two() {

        lock.lock();
        try {
            while (flag != 2){
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"\ttwo");
            flag = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void three() {

        lock.lock();
        try {
            while (flag != 3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"\tthree");
            flag = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        PrintInOrderDemo3 printInOrder = new PrintInOrderDemo3();

        for (int i=1;i<=5;i++){
            new Thread(() -> printInOrder.one(), "A").start();

            new Thread(() -> printInOrder.two(), "B").start();

            new Thread(printInOrder::three, "C").start();
        }
    }
}
