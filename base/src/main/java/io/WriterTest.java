package io;

import java.io.FileWriter;

/**
 * @Description:
 * flush：刷新缓冲区，流对象可以继续使用
 * close:先刷新缓冲区，然后通知系统释放资源。流对象不可以再被使用了
 * @author: yjl
 * @date: 2022年11月01日 16:39
 */
public class WriterTest {
    public void testA()throws Exception{
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("abc.txt");
        // 写出数据
        fw.write(97); // 写出第1个字符
        fw.write('b'); // 写出第2个字符
        fw.write('C'); // 写出第3个字符

        //关闭资源时,与FileOutputStream不同。 如果不关闭,数据只是保存到缓冲区，并未保存到文件。
         fw.flush();
         fw.close();
    }
    public static void main(String[] args) throws Exception{
        WriterTest test=new WriterTest();
        test.testA();
    }
}
