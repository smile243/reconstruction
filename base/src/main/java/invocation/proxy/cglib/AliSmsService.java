package invocation.proxy.cglib;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 14:26
 */
public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
