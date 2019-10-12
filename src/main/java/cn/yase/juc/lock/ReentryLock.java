package cn.yase.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁(递归锁)
 *      1.什么是可重入锁？
 *          指在统一线程外层函数获得锁之后，内层递归函数依然能获取该锁的代码。
 *          在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。
 *
 *          也就是说: 线程可以进入任何一个它已经拥有的锁所同步着的代码块。
 *          比如说：有个方法1是synchronized锁着的，方法里面还有个synchronized
 *          锁着的方法2，那么只要拥有了method1的锁，那么也就拥有了方法2的锁
 *
 *          public sync void method1(){
 *              method2();
 *          }
 *          public sync void method2(){
 *
 *          }
 *
 *
 *      2.可重入锁的作用是什么？
 *          可重入锁的最大作用是避免死锁。
 *
 *
 *      3.有哪些是可重入锁？
 *          3.1 ReentrantLock/Synchronized 就是一个典型的可重入锁。
 *
 *      4.补充:
 *          lock、unlock/ lock lock unlock unlock 都是能编译通过且运行成功(也就是lock的数量和unlock的数量一致就行)
 *
 *
 * @author yase
 * @data 2019/10/11
 */
public class ReentryLock {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sendSMS();
        },"t1").start();

        new Thread(()->{
            phone.sendSMS();
        },"t2").start();

        //======================================================

        new Thread(phone,"t3").start();
        new Thread(phone,"t4").start();

    }
}

class Phone implements Runnable{
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getId()+"\t invoked sendSMS()");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getId()+"\t invoked sendEmail()");
    }


    //======================================================

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tinvoked get()");
            set();
        }finally {
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tinvoked set()");
        }finally {
            lock.unlock();
        }
    }

}