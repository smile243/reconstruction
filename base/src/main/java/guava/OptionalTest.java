package guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

/**
 * @author yjl
 * @Description
 * @date 2022/8/8 22:01
 */
public class OptionalTest {
    public static void main(String[] args) {
        //Optional<String> test=Optional.of(null);
        Optional<String> test2=Optional.absent();
        Optional<String> test3=Optional.fromNullable("null");
        System.out.println(test3.get());
        String a=MoreObjects.firstNonNull("4","3");
        System.out.println(a);
    }
}
