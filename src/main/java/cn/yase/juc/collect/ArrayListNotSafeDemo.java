package cn.yase.juc.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * ArrayList线程不安全 当执行下面这段代码的时候(System.out.println(list);)
 *
 * 1.故障现象:
 *      java.util.ConcurrentModificationException
 *
 * 2.导致原因:
 *
 *
 * 3.解决方案:
 *      3.1 new Vector<>()
 *      3.2 Collections.synchronizedList(new ArrayList<>());
 *      3.3 new CopyOnWriteArrayList<>() (写时复制/读写分析的思想)
 *          主要思路:读都在一张纸上,写的时候将读的纸复制一份在新的纸上写上，然后通知其他读都到新的纸上读。
 *
 * 
 * @author yase
 * @since 2019/10/11 下午10:42
 */
public class ArrayListNotSafeDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
