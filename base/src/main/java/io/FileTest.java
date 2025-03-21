package io;

import java.io.File;

/**
 * @Description:
 * mkdirs是多级目录
 * mkdir只是单级目录
 *
 * @author: yjl
 * @date: 2022年10月25日 13:25
 */
public class FileTest {
    public void test1()throws Exception{
        String path="F:\\123.txt";
        File f=new File(path);
        System.out.println("文件绝对路径:"+f.getAbsolutePath());
        System.out.println("文件构造路径:"+f.getPath());
        System.out.println("文件名称:"+f.getName());
        System.out.println("文件长度:"+f.length()+"字节");
        f.delete();
        f.mkdir();
        f.mkdirs();
        System.out.println("文件是否真实存在:"+f.exists());
        System.out.println("文件是创建过则false:"+f.createNewFile());
        System.out.println("文件是创建过则false:"+f.createNewFile());
        System.out.println("文件是否真实存在:"+f.exists());

        File f2=new File("123.txt");
        System.out.println("文件绝对路径:"+f2.getAbsolutePath());
    }

    public static void main(String[] args) throws Exception{
        FileTest fileTest=new FileTest();
        fileTest.test1();
    }
}
