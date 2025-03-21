package base;

import com.github.phantomthief.pool.KeyAffinityExecutor;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 魔改线程池，原理是使用了多个只有1长度的fixedpoolthread
 * @Goal: 需要一个确保投递进来的任务按某个维度换分出任务，然后按照任务提交顺序依次执行的线程池
 * @author: yjl
 * @date: 2021年12月30日 14:35
 */
public class NbThreadPoolTest {
    public static class CoderDoSomeThing{
        private String name;
        private String doSomeThing;

        public CoderDoSomeThing(String name, String doSomeThing) {
            this.name = name;
            this.doSomeThing = doSomeThing;
        }

        @Override
        public String toString() {
            return "CoderDoSomeThing{" +
                    "name='" + name + '\'' +
                    ", doSomeThing='" + doSomeThing + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDoSomeThing() {
            return doSomeThing;
        }

        public void setDoSomeThing(String doSomeThing) {
            this.doSomeThing = doSomeThing;
        }
    }
    public static void main(String[] args) {
        KeyAffinityExecutor executorService=KeyAffinityExecutor.newSerializingExecutor(8,200,"TEST-%d");
        List<CoderDoSomeThing> coderDoSomeThingList = new ArrayList<>();
        coderDoSomeThingList.add(new CoderDoSomeThing("富贵", "启动Idea"));
        coderDoSomeThingList.add(new CoderDoSomeThing("富贵", "搞数据库,连tomcat,crud一顿输出"));
        coderDoSomeThingList.add(new CoderDoSomeThing("富贵", "嘴角疯狂上扬"));
        coderDoSomeThingList.add(new CoderDoSomeThing("富贵", "接口访问报错"));
        coderDoSomeThingList.add(new CoderDoSomeThing("富贵", "心态崩了，卸载Idea"));

        coderDoSomeThingList.add(new CoderDoSomeThing("旺财", "启动Idea"));
        coderDoSomeThingList.add(new CoderDoSomeThing("旺财", "搞数据库,连tomcat,crud一顿输出"));
        coderDoSomeThingList.add(new CoderDoSomeThing("旺财", "嘴角疯狂上扬"));
        coderDoSomeThingList.add(new CoderDoSomeThing("旺财", "接口访问报错"));
        coderDoSomeThingList.add(new CoderDoSomeThing("旺财", "心态崩了，卸载Idea"));
        //executeEx的KEY 是区分某类任务的维度
        coderDoSomeThingList.forEach(o->{
         executorService.executeEx(o.getName(),()->{
             System.out.println(Thread.currentThread().getName()+":"+o.toString());
         });
        });
    }

}
