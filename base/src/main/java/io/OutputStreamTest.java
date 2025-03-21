package io;

import java.io.FileOutputStream;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月01日 13:24
 */
public class OutputStreamTest {
    /***
     * 每次都会清空
     * @throws Exception
     */
    public void testA() throws Exception{
        FileOutputStream outputStream = new FileOutputStream("abc.txt");
        outputStream.write(97);
        outputStream.write(10);
        //一个字符3个字节
        byte[] b = "麻麻我想吃烤山药".getBytes();
        outputStream.write(b,0,24);
        outputStream.close();
    }

    /***
     * 追加式写入数据
     * @throws Exception
     */
    public void testB() throws Exception{
        FileOutputStream outputStream = new FileOutputStream("abc.txt",true);
        outputStream.write(97);
        outputStream.write(10);
        //一个字符3个字节
        byte[] b = "麻麻我想吃烤山药".getBytes();
        outputStream.write(b,0,24);
        outputStream.close();
    }
    public static void main(String[] args) throws Exception{
        OutputStreamTest test=new OutputStreamTest();
        test.testB();
    }
}
