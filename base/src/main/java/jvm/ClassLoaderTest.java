package jvm;

/**
 * @Description: 
 * @author: yjl
 * @date: 2022年01月27日 14:17
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        //java核心类库都用引导类加载器Bootstrap加载的,是获取不到的 为null，所以显示null的都是由bootstrap加载的
        ClassLoader classLoader=String.class.getClassLoader();
        System.out.println(classLoader);
        //默认使用系统类加载器
        ClassLoader classLoader1=ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader1);
        //获取当前线程上下文加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }

}
