package invocation;

import lombok.ToString;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年11月02日 13:51
 */
@ToString
public class TargetObject {
    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("value is " + value);
    }
}
