package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 关键 list.remove(item)
 * 先看一下class文件，foreach语法糖
 * modCount是什么，干啥的  记录此列表已被结构修改的次数； 当remove或者add的时候modCount会++
 * expectedModCount是什么，干啥的 是ArrayList中一个名叫Itr内部类的成员变量；Itr初始化或者remove方法调用后会将modCount赋值给expectedModCount
 * 出错的最终原因是ArrayList(很重要，看CopyOnWriteArrayList)的快速失败机制中list.remove只会修改modCount++,expectModCount是不变的，只有iterator的remove才会执行expectModCount=modCount
 * @author: yjl
 * @date: 2021年10月21日 10:21
 */
public class ForeachTest {
    public static void main(String[] args) {
        //JUC在一致性的是线上和普通的其他类不一样的，例如这里的CopyOnWriteArrayList是最终一致性
        //List<String> list = new CopyOnWriteArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println("before move :"+list);
//        for (String item : list) {
//            if ("2".equals(item)) {
//                list.remove(item);
//            }
//        }
        Iterator<String> var1=list.iterator();
        int loopTime=1;
        while(var1.hasNext()){
            System.out.println("loopTime= "+loopTime);
            loopTime++;
            String item=(String)var1.next();
            if("2".equals(item)){
                list.remove(item);
            }
        }
        System.out.println("after move :"+list);
    }
}
