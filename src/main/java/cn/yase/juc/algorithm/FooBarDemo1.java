package cn.yase.juc.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 交替打印 FooBar:
 *      两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 示例1:
 *      输入: n = 1
 *      输出: "foobar"
 *      解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * 示例2:
 *      输入: n = 2
 *      输出: "foobarfoobar"
 *      解释: "foobar" 将被输出两次。
 *
 * 解析:
 *      就是要实现多个线程执行，先执行 foo方法中的for循环一次后，再执行bar方法中的for循环一次
 *
 * 解决方案：
 *      解法1 : 使用 ReentrantLock + condition
 *
 * @author yase
 * @since 2019/10/12 下午2:22
 */
public class FooBarDemo1 {

    private int n;

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    private volatile boolean fooExecute = true;

    public FooBarDemo1(int n) {
        this.n = n;
    }

    public void foo()  {
        for (int i = 0; i < n; i++) {
            // 上锁
            lock.lock();
            try {
                // 阻塞
                while (!fooExecute){
                    condition1.await();
                }

                // 执行
                System.out.print("foo");

                //通知
                fooExecute = false;
                condition2.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }

    public void bar() {
        for (int i = 0; i < n; i++) {
            // 上锁
            lock.lock();
            try {
                // 阻塞
                while (fooExecute){
                    condition2.await();
                }

                // 执行
                System.out.print("bar");

                //通知
                fooExecute = true;
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 解锁
                lock.unlock();
            }

        }
    }


    public static void main(String[] args) {
        FooBarDemo1 fooBarDemo1 = new FooBarDemo1(2);
        new Thread(()->fooBarDemo1.foo()).start();
        new Thread(()->fooBarDemo1.bar()).start();
    }

}
