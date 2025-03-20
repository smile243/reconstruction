package concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 这里会有ABA问题,使用AtomicStampedReference
 * rt.jar包类必须使用bootstrap加载器加载，而main函数所在类是AppClassLoader加载
 *
 * @author yjl
 */
public class UnsafeTest {
    //=Unsafe.getUnsafe();这样初始化会涉及到安全问题，被禁止的;
    static final Unsafe unsafe;

    static final long stateOffset;
    //初始值为2
    private volatile long state = 2;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            //获取类变量偏移值
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        //如果test对象的偏移量为stateOffset且变量值为1，则将state设置为1
        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 2, 1);
        System.out.println(success);
        System.out.println(test.state);
        System.out.println(stateOffset);

    }
}
