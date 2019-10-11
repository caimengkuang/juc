package cn.yase.juc.volatile1;

/**
 * @author yase
 * @since 2019/10/11 下午8:43
 */
public class SingletonDemo {

    private static SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法");
    }

    public static synchronized SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> SingletonDemo.getInstance(), String.valueOf(i)).start();
        }
    }

}
