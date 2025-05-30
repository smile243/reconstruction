package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author &{USER}
 * @date 2021/12/2022:59
 */
public class CreateHashCodeSomeUtil {

    /**
     * 种子数据：两个长度为 2 的 hashCode 一样的字符串
     */
    private static final String[] SEED = new String[]{"Aa", "BB"};

    /**
     * 生成 2 的 n 次方个 HashCode 一样的字符串的集合
     */
    public static List<String> hashCodeSomeList(int n) {
        List<String> initList = new ArrayList<String>(Arrays.asList(SEED));
        for (int i = 1; i < n; i++) {
            initList = createByList(initList);
        }
        return initList;
    }

    public static List<String> createByList(List<String> list) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < SEED.length; ++i) {
            for (String str : list) {
                result.add(SEED[i] + str);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(hashCodeSomeList(1));
        System.out.println(hashCodeSomeList(2));
        System.out.println(hashCodeSomeList(3));
    }
}

