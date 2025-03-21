package io;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月01日 14:28
 */
public class InputStreamTest {
    public void testA() throws IOException {
        // 使用文件名称创建流对象
        FileInputStream fos = new FileInputStream("abc.txt");
        int len;
        // 定义字节数组，作为装字节数据的容器
        byte[] b = new byte[1024];
        // 循环读取
        while ((len = fos.read(b))!=-1) {
            System.out.println(new String(b,0,len));
        }
        // 关闭资源
        fos.close();
    }
    public static void main(String[] args) throws Exception{
        InputStreamTest test=new InputStreamTest();
        test.testA();
    }
}
