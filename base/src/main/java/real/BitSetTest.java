package real;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yjl
 * @Description 10亿手机号码去重,例如18114584885，因为第一位都是1，所以只要存10位，但是bitset只能set的值为int，只能存储2亿左右
 * 问题:为什么set只能int?
 *
 * @date 2023/1/1 18:52
 */
public class BitSetTest {
    public static void main(String[] args) {
        Map<String,BitSet> map=new HashMap<>();
        //根据号码段分桶,拿两位分桶是因为set只能存储两亿，如果一位的话不够(214748364往上就存不了)
        BitSet bitSet81=map.computeIfAbsent("81",key->new BitSet());
        bitSet81.set(14584885);
        bitSet81.set(214748364);
        System.out.println(Integer.MAX_VALUE);
        System.out.println("有值的位数:"+bitSet81.cardinality());
        System.out.println("所在位是否有值:"+bitSet81.get(14584885));
    }
}
