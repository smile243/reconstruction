package invocation.proxy.jdkDynamic;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 14:22
 */
public class Main {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");
    }
}
