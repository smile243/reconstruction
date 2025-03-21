package io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description: 字符流不可操作图片，视频等非文本文件
 * @author: yjl
 * @date: 2022年11月01日 16:11
 */
public class ReaderTest {
    public void testA() throws IOException {
        FileReader fr = null;
        try {
            // 使用文件名称创建流对象
            fr = new FileReader("abc.txt");
            // 定义变量，保存数据
            int b;
            // 循环读取
            while ((b = fr.read()) != -1) {
                System.out.println((char) b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (Objects.nonNull(fr)) {
                fr.close();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        ReaderTest test = new ReaderTest();
        test.testA();
    }
}
