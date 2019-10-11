package cn.yase.juc.collect;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap线程不安全
 *      解决方案:
 *          ConcurrentHashMap
 *
 * @author yase
 * @since 2019/10/11 下午11:11
 */
public class HashMapNotSafeDemo {

    public static void main(String[] args) {

//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        HashMap<String,String> map = new HashMap<>();

        for (int i=1;i<=30;i++){
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));

                // 这行会出现并发修改异常
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
