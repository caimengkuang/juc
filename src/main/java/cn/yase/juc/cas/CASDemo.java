package cn.yase.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS
 *  ## 是什么？
 *      他是一条CPU并发原语(原语执行是必须连续的，在执行过程中不允许被中断)，
 *      他的功能是判断内存某个位置的值是否为预期值，如果是则更新为新值。
 *
 *
 *  ## 原理：
 *      1.unsafe类
 *          根据内存偏移地址获取数据
 *      2.自旋锁
 *
 *
 *  ## 底层代码:
 *  public final int getAndAddInt(Object var1,long var2,int var4){
 *      int var5;
 *      do {
 *          // 根据var1(对象)、var2(偏移量) 获取该位置上的真实值
 *          var5 = this.getIntVolatile(var1,var2);
 *
 *          // 如果根据var1 和var2获取该位置上的实际值，和预期值var5是否一致，是则将预期值+要加的值(var4)（这是一个原子操作）。负责在此执行上一步操作。
 *      } while(!this.compareAndSwapInt(var1,var2,var5,var5+var4));
 *
 *      return var5;
 *  }
 *
 *  ## 缺点
 *      1.如果CAS失败，会一致进行尝试，CPU带来很大的开销
 *      2.只能保证一个共享变量的原子操作(对多个共享变量操作时,循环CAS就无法保证操作的原子性，这个时候可以用锁来保证原子性)
 *      3.引出ABA问题
 *
 * @author yase
 * @since 2019/10/11 下午9:46
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(1);

        System.out.println(atomicInteger.compareAndSet(1,10));
        System.out.println(atomicInteger.compareAndSet(1,100));

        atomicInteger.getAndIncrement();
    }

}
