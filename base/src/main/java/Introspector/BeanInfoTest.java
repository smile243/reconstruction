package Introspector;

/**
 * @author yjl
 * @Description
 * @date 2022/10/26 21:19
 */
public class BeanInfoTest {
    public static void main(String[] args) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("yjl");

        try {
            BeanInfoUtil.getProperty(userInfo,"userName");
            BeanInfoUtil.setProperty(userInfo,"userName");
            BeanInfoUtil.getProperty(userInfo,"userName");
            BeanInfoUtil.setPropertyByIntrospector(userInfo,"userName");
            BeanInfoUtil.getPropertyByIntrospector(userInfo,"userName");
            //age的类型不对，所以会报错
            BeanInfoUtil.setProperty(userInfo,"age");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
