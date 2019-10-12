package cn.yase.juc.lock;

/**
 *
 * 公平锁／非公平锁
 *
 *      1.公平锁:
 *          指多个线程按申请锁的顺序获取锁，类似排队打饭
 *      2.非公平锁
 *          指多个线程不按申请锁的顺序获取锁。有可能后申请的线程比先申请的线程优先获取锁。有可能造成线程饥饿
 *          优点:吞吐量比公平锁大
 *
 *      ReentrantLock 默认非公平锁，可设置为公平锁
 *
 *      Synchronized : 非公平锁
 *
 * @author yase
 * @since 2019/10/11 下午11:16
 */
public class FairAndUnfairLock {


}
