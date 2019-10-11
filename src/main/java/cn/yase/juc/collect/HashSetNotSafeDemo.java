package cn.yase.juc.collect;

/**
 *
 * HashSet线程不安全
 *      解决方案:
 *          1.new Vector<>()
 *          2.Collections.synchronizedList(new ArrayList<>());
 *          3.new CopeOnWriteArraySet<>()(底层还是CopeOnWriteArrayList()<>)
 *
 * HashSet底层是HashMap。HashSet底层只关心key,value是一个固定的值
 *
 * @author yase
 * @since 2019/10/11 下午11:07
 */
public class HashSetNotSafeDemo {

    public static void main(String[] args) {

    }

}
