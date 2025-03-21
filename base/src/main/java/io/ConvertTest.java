package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author yjl
 * @Description
 * @date 2022/11/1 21:01
 */
public class ConvertTest {
    public void testA() throws IOException {
        // 定义文件路径,文件为gbk编码
        String FileName = "aaa.txt";
        // 创建流对象,默认UTF8编码
        InputStreamReader isr = new InputStreamReader(new FileInputStream(FileName));
        // 创建流对象,指定GBK编码
        InputStreamReader isr2 = new InputStreamReader(new FileInputStream(FileName), "GBK");
        // 定义变量,保存字符
        int read;
        // 使用默认编码字符流读取,乱码
        while ((read = isr.read()) != -1) {
            System.out.print((char) read);
        }
        System.out.println();
        isr.close();

        // 使用指定编码字符流读取,正常解析
        while ((read = isr2.read()) != -1) {
            System.out.print((char) read);
        }
        System.out.println();
        isr2.close();
    }

    public void testB() throws IOException {
        // 定义文件路径
        String FileName = "bbb.txt";
        // 创建流对象,默认UTF8编码
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(FileName));
        // 写出数据
        osw.write("哥敢");
        osw.close();

        // 定义文件路径
        String FileName2 = "ccc.txt";
        // 创建流对象,指定GBK编码
        OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream(FileName2),"GBK");
        // 写出数据
        osw2.write("摸屎");
        osw2.close();
    }

    public static void main(String[] args) throws Exception {
        ConvertTest test = new ConvertTest();
        test.testB();
    }
}
