package base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对hashmap排序,linkedhashmap比TreeMap灵活，可以根据key或者value排序
 * @author yjl
 * @since 2024/5/31
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        map.put("bac","yjl");map.put("abc","yjl");map.put("3","yjl");
        Map<String, String> sortedMap = map.entrySet()
                .stream()
                //comparingByValue
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        System.out.println(map);
        System.out.println(sortedMap);
    }
}
