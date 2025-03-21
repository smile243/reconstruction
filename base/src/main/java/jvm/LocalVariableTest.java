package jvm;

import java.util.Date;

/**
 * @Description: 局部变量表
 * @author: yjl
 * @date: 2022年02月07日 15:47
 */
public class LocalVariableTest {
    private int count=0;
    public static void main(String[] args) {
        LocalVariableTest test=new LocalVariableTest();
        int num=10;
        test.test1();
        //因为this变量不存在于当前方法的局部变量表中
        //System.out.println(this.count);
    }

    private void test1() {
        Date date=new Date();
        String name="yjl";
        String info=test2(date,name);
        System.out.println(date+name);
    }

    private String test2(Date date, String name) {
        date=null;
        name="test";
        double weight=130.4;
        char gender='男';
        return date+name;
    }

    /***
     * 变量分类:
     * 1基本数据类型；  2引用数据类型
     * 1成员变量：
     *      类变量:linking的prepare阶段给类变量默认赋值，initial阶段给类变量显式赋值即静态代码块赋值
     *      实例变量；随着对象创建，会在对空间分配实例变量空间，并进行默认赋值
     * 2局部变量:在使用前，必须显式赋值
     *
     */
}
