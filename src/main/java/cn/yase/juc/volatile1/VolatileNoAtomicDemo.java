package cn.yase.juc.volatile1;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 不能保证原子性验证
 *
 * 原子性: 不可分割，完整性，也即某个线程正在做某个业务时，中间不可以被加塞或者分割。需要整体完整，要么同时成功，要么同时失败。
 *
 * volatile不保证原子性的原因:
 *      number ++ 被拆分为3个指令:
 *          1.获取原始址
 *          2.执行number加1操作
 *          3.把累加后的值写回主内存中
 *      存在情况:
 *          线程1、线程2都获取到了主内存中的值1，都在各自的各自内存中进行了加1操作，线程2挂起，线程1
 *          将2写回到主内存中，此时线程2唤起将2又写会到了主内存中。则主内存的值为2 ，而照理来说累加两次
 *          应该为3。
 *
 * 如何解决原子性
 *      1.加sync
 *      2.atomic
 *
 * @author yase
 * @since 2019/10/11 下午5:55
 */
public class VolatileNoAtomicDemo {

    static volatile int number = 1;

    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(100,100,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>(10000));

        for (int i=0;i<=10000;i++){
            pool.execute(()->{number++;});
        }

        // 按理来说 number 应该是 10001,但是实际打印出来确是 小于10001的值
        System.out.println(number);

        for (int i=0;i<10000;i++){
            atomicInteger.incrementAndGet();
        }

        System.out.println(atomicInteger.get());

        pool.shutdown();
    }

}
