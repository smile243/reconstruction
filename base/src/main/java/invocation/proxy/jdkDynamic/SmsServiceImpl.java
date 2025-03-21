package invocation.proxy.jdkDynamic;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 14:14
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
