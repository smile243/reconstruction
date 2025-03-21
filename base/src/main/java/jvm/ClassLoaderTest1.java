package jvm;


/**
 * @Description:
 * @author: yjl
 * @date: 2022年08月06日 13:52
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {
//        System.out.println("========启动类加载器==========");
//        //获取bootstrap能够加载的api的路径
//        URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
//        for(URL element:urls){
//            System.out.println(element.toExternalForm());
//        }
        System.out.println("=====扩展类加载器======");
        String extDirs=System.getProperty("java.ext.dirs");
        for (String path: extDirs.split(";")){
            System.out.println(path);
        }
    }
}
