package Introspector;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yjl
 * @Description
 * @date 2022/10/26 21:11
 */
@Getter
@Setter
public class UserInfo {
    private long userId;
    private String userName;
    private int age;
    private String emailAddress;
}
