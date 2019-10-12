package cn.yase.juc.algorithm;

/**
 * 解法2 : 使用 volatile + 自旋
 *
 * @author yase
 * @since 2019/10/12 上午11:02
 */
public class PrintInOrderDemo2 {
    // volatile的可见性
    private volatile int flag = 1;

    public void one() {
        while (flag != 1) {
        }
        System.out.println(Thread.currentThread().getName() + "\tone");
        flag = 2;
    }

    public void two() {
        while (flag != 2) {
        }
        System.out.println(Thread.currentThread().getName() + "\ttwo");
        flag = 3;
    }

    public void three() {
        while (flag != 3) {
        }
        System.out.println(Thread.currentThread().getName() + "\tthree");
    }

    public static void main(String[] args) {

        PrintInOrderDemo2 printInOrder = new PrintInOrderDemo2();

        new Thread(() -> printInOrder.one(), "A").start();

        new Thread(() -> printInOrder.two(), "B").start();

        new Thread(printInOrder::three, "C").start();
    }

}
