package cn.yase.juc.volatile1;

/**
 *
 * volatile可见性验证
 *
 * @author yase
 * @since 2019/10/11 下午5:42
 */
public class VolatileVisibilityDemo {


    static volatile int noBlockingNumber = 1;

    static int number = 1;

    static void blocking(){

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                // 将number修改为0
                number = 0;
            } catch (InterruptedException e) {

            }
        }, "线程A").start();

        // 如果等于1的话，死循环,等待
        while (number == 1) {}
        System.out.println("修改为0了");
    }

    static void noBlocking(){

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                // 将number修改为0
                noBlockingNumber = 0;
            } catch (InterruptedException e) {

            }
        }, "线程A").start();

        // 如果等于1的话，死循环,等待
        while (noBlockingNumber == 1) {}

        System.out.println("noBlockingNumber修改为0了");
    }


    public static void main(String[] args) throws InterruptedException {

        // 因为有volatile修饰，修改了 noBlockingNumber 值，另外一个线程能知道所以不会被阻塞住
        noBlocking();

        // 因为没有volatile修饰，修改了 number 值，另外一个线程不能知道所以不会被阻塞住
        blocking();
    }

}
