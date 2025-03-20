package concurrent;

/**
 * 引用逸出
 *
 * @author yjl
 */
public class FinalReferenceEscapeExample {
    final int i;
    static FinalReferenceEscapeExample obj;

    public FinalReferenceEscapeExample() {
        i = 1;
        obj = this;// this引用在这里逸出
    }

    public static void writer() {
        new FinalReferenceEscapeExample();
    }

    public static void reader() {
        if (null != obj) {
            int temp = obj.i;
        }
    }
}
