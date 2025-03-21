package invocation.proxy.jdkDynamic;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 14:20
 */
public class JdkProxyFactory {
    //获取某个类的代理对象
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                // 目标类的类加载
                target.getClass().getClassLoader(),
                // 代理需要实现的接口，可指定多个
                target.getClass().getInterfaces(),
                // 代理对象对应的自定义 InvocationHandler
                new DebugInvocationHandler(target)
        );
    }
}
