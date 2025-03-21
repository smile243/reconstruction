package guava;

import com.google.common.base.Preconditions;

/**
 * @author yjl
 * @Description
 * @date 2022/8/8 22:56
 */
public class PreconditionsTest {
    public static void main(String[] args) {
        int i=1;
        int j=0;
        //Preconditions.checkArgument(i>2,"Argument was %s but expected nonnegative", i);
        //Preconditions.checkArgument(i < j, "Expected i < j, but %s >= %s", i, j);
        Integer a=null;
        String b="123";
        Preconditions.checkNotNull(a,"%s 不准为空",b);
    }
}
