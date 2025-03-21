package invocation;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @author: yjl
 * @date: 2023年02月24日 10:48
 */
@Slf4j
public class ClassTest {
    public static void main(String[] args) throws Exception{
        TargetObject targetObject=new TargetObject();
        Class targetClass=targetObject.getClass();
        // 获取Class对象的三种方式
        log.info("根据类名:  \t" + TargetObject.class);
        log.info("根据对象:  \t" + targetObject.getClass());
        log.info("根据全限定类名:\t" + Class.forName("invocation.TargetObject"));
        // 常用的方法
        log.info("获取全限定类名:\t" + targetClass.getName());
        log.info("获取类名:\t" + targetClass.getSimpleName());
        log.info("实例化:\t" + targetClass.newInstance());
    }
}
