package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义类加载器分为重载classloader和urlClassloader
 * @author yjl
 */
public class MyClassLoaderParentFirst extends ClassLoader{
    private ClassLoader jdkClassLoader;
    private Map<String, String> classPathMap = new HashMap<>();

    public MyClassLoaderParentFirst(ClassLoader jdkClassLoader) {
        this.jdkClassLoader=jdkClassLoader;
        classPathMap.put("classloader.TestA", "/Users/yujiale/Documents/reconstruction/base/target/classes/classloader/TestA.class");
        classPathMap.put("classloader.TestB", "/Users/yujiale/Documents/reconstruction/base/target/classes/classloader/TestB.class");
    }
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (!file.exists()) {
            throw new ClassNotFoundException();
        }
        byte[] classBytes = getClassData(file);
        if (classBytes.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(name,classBytes, 0, classBytes.length);
    }

    //破坏双亲委派机制
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> result = null;
        try {
            //这里要使用 JDK 的类加载器加载 java.lang 包里面的类
            result = jdkClassLoader.loadClass(name);
        } catch (Exception e) {
            //忽略
        }
        if (result != null) {
            return result;
        }
        String classPath = classPathMap.get(name);
        /***
         * testB依附的类用appclassloader去加载
         */
        if(null==classPath){
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }
        File file = new File(classPath);
        if (!file.exists()) {
            throw new ClassNotFoundException();
        }

        byte[] classBytes = getClassData(file);
        if (classBytes.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(name,classBytes, 0, classBytes.length);
    }

    private byte[] getClassData(File file) {
        try (InputStream ins = new FileInputStream(file); ByteArrayOutputStream baos = new
                ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesNumRead;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[] {};
    }
}
