package real;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 流式实际场景测试
 * @author:
 * @date: 2022年10月19日 13:35
 */
public class StreamTest {
    public static List<Student> init(){
        Student studentA=new Student("zs",18,Arrays.asList(1,2),new BigDecimal(182),new BigDecimal(80));
        Student studentB=new Student("ls",null,Arrays.asList(3,4),new BigDecimal(172),new BigDecimal(64));
        List<Student> students= new ArrayList<>();
        students.add(studentA);
        students.add(studentB);
        return students;
    }

    /***
     * 如果过滤剩下的list为空，可以在这个list上addAll吗？
     */
    public void addAll(){
        List<Student> students=StreamTest.init();
        //map放在filter前
        List<Integer> age=students.stream().map(Student::getAge).filter(oAge -> Objects.equals(11, oAge)).collect(Collectors.toList());
        age.addAll(Arrays.asList(1,2));
        System.out.println(age);
    }

    /***
     * flat操作可以把list的stream平铺吗
     */
    public void flatList(){
        List<Student> students=StreamTest.init();
        List<Integer> list=students.stream().flatMap(o->o.getList().stream()).distinct().collect(Collectors.toList());
        System.out.println(list);
    }
    public static void main(String[] args) {
        StreamTest test=new StreamTest();
        test.breakTest();
    }

    /***
     * filter是否会影响到数据源?
     */
    public void filterTest(){
        List<Student> students=StreamTest.init();
        Map<String,List<Student>> map=new HashMap<>();
        map.put("a",students);
        for(Map.Entry<String,List<Student>> entry:map.entrySet()){
            List<Student> list =entry.getValue();
            //collect是创建一个新的对象
            list=list.stream().filter(o->"zs".equals(o.getName())).collect(Collectors.toList());
            //filter必须重新赋值
            //entry.setValue(list);
            list.get(0).setName("yjl");
        }
        System.out.println(map);
    }

    /***
     * 一个流中实现多个字段计算
     * @return
     */
    public BigDecimal twoNumStreamTest(){
        List<Student> students=StreamTest.init();
        return students.stream().map(o->o.getHeight().subtract(o.getWeight())).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public void quote(){
        List<Student> students=StreamTest.init();
        Map<String,Student> map=students.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
        students.get(0).setName("wemz");
        System.out.println(map);
    }

    public void optionalIsPresent(){
        List<Student> students=StreamTest.init();
        Optional.ofNullable(students.get(0).getAge()).ifPresent(o->students.get(0).setAge(555));
        Optional.ofNullable(students.get(0)).ifPresent(o->o.setAge(555));
    }

    /***
     * 流中使用return而不是continue
     */
    public void breakTest(){
        List<Student> students=StreamTest.init();
        students.forEach(o->{
            if(Objects.isNull(o.getAge())){
                return;
            }
            System.out.println(o);
        });
    }

}
