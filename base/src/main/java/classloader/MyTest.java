package classloader;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年03月02日 15:06
 */
public class MyTest {
    public static void main(String[] args) throws Exception {
        MyClassLoaderParentFirst myClassLoaderParentFirst = new MyClassLoaderParentFirst(Thread.currentThread().getContextClassLoader().getParent());
        Class<?> testAClass = myClassLoaderParentFirst.findClass("classloader.TestA");
        Method mainMethod = testAClass.getDeclaredMethod("main", String[].class);
        Object[] objects=new Object[]{args};
        mainMethod.invoke(null, objects);
    }
}
