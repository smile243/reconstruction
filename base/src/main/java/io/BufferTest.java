package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author yjl
 * @Description 缓冲流速度极快
 * @date 2022/11/1 20:13
 */
public class BufferTest {
    public void testA() {
        // 记录开始时间
        long start = System.currentTimeMillis();
        // 创建流对象
        try (
                BufferedInputStream fis = new BufferedInputStream(new FileInputStream("E:\\pdf\\Java并发编程之美.pdf"));
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream("copyPy.pdf"));
        ) {
            // 读写数据
//            int b;
//            while ((b = fis.read()) != -1) {
//                fos.write(b);
//            }
            /**
             * @Description: 更快的写法
             * @Date: 2022/11/1
             **/
            int len;
            byte[] bytes = new byte[32*1024];
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("普通流复制时间:" + (end - start) + " 毫秒");
    }

    /**
     * @Description: 排序
     * @Date: 2022/11/1
     **/
    public void testB() throws IOException {
        // 创建map集合,保存文本数据,键为序号,值为文字
        HashMap<String, String> lineMap = new HashMap<>();

        // 创建流对象  源
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));
        //目标
        BufferedWriter bw = new BufferedWriter(new FileWriter("b.txt"));

        // 读取数据
        String line  = null;
        while ((line = br.readLine())!=null) {
            // 解析文本
            String[] split = line.split("\\.");
            // 保存到集合
            lineMap.put(split[0],split[1]);
        }
        // 释放资源
        br.close();

        // 遍历map集合
        for (int i = 1; i <= lineMap.size(); i++) {
            String key = String.valueOf(i);
            // 获取map中文本
            String value = lineMap.get(key);
            // 写出拼接文本
            bw.write(key+"."+value);
            // 写出换行
            bw.newLine();
        }
        // 释放资源
        bw.close();
    }

    public static void main(String[] args) throws Exception{
        BufferTest test = new BufferTest();
        test.testB();
    }
}
