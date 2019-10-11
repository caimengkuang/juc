package cn.yase.juc.volatile1;

/**
 *
 * volatile在DCL中应用(保证有序性)
 * DCL (Double check lock双端检锁机制)
 *
 * instance = new DCLSingleDemo();可以分为下面3步完成:
 *      1.分配对象内存空间
 *      2.初始化对象
 *      3.设置instance指向刚分配的内存地址,此时 install != null
 * 但是2、3不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并有改变,因此这种重排是允许的。
 * 所以重排后的顺序可能为:
 *      1.分配对象内存空间
 *      3.设置instance指向刚分配的内存地址,此时 install != null,但是对象没有初始化完成
 *      2.初始化对象
 * 这种情况下，当一个线程在执行完3，但是还没有执行完2时，另外一个线程判断了第一个  instance == null，此时
 * 判断结果为 false，直接return install 而此时还没进行初始化对象，则返回为null.
 *
 * 解决方案 install 添加volatil，进制指令重排
 *
 * @author yase
 * @since 2019/10/11 下午8:48
 */
public class DCLSingleDemo {

    private static volatile DCLSingleDemo instance = null;

    private DCLSingleDemo() {
        System.out.println("构造器");
    }

    public static DCLSingleDemo getInstance(){
        if (instance == null){
            synchronized (DCLSingleDemo.class){
                if (instance == null){
                    instance = new DCLSingleDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

        for (int i=1;i<=100;i++){

        }

    }

}
