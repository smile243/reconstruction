package base;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description:
 * @author: yjl
 * @date: 2024年02月03日 10:24
 */
public class TreeMapTest {
    public static void main(String[] args) {
        Map<String,Integer> a=new HashMap<>();
        a.put("你好",3);
        a.put("士大夫",4);
        a.put("风格化风格化",5);
        a.put("阿斯顿",1);
        a.put("士大夫感到",2);
        Map<String,String> map=new TreeMap<>(Comparator.comparingInt(a::get));
        map.put("你好","abc");
        map.put("士大夫","asd");
        map.put("风格化风格化","vcxv");
        map.put("阿斯顿","2w4r");
        map.put("士大夫感到","sdf");
        System.out.println(map);
    }
}
