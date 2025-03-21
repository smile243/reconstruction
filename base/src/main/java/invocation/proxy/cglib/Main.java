package invocation.proxy.cglib;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 14:27
 */
public class Main {
    public static void main(String[] args) {
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
    }
}
