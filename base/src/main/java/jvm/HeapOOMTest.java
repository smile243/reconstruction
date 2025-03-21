package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * dump下来的转储快照使用jvisualvm分析
 * -Xms20m -Xmx20m 限制java堆大小20MB，不可扩展
 * -XX: +HeapDumpOnOutOfMemoryError   让虚拟机在出现内存溢出异常事Dump出当前的内存堆转储快照
 * -XX:HeapDumpPath=/tmp/heapdump.hprof 指定导出堆信息时的路径或文件名
 * @author: yjl
 * @date: 2021年10月19日 16:01
 */
public class HeapOOMTest {
    static class OOMObject{
    }
    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<>();
        while(true){
            list.add(new OOMObject());
        }
    }
}
