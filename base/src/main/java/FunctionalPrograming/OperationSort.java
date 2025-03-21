package FunctionalPrograming;

import real.StreamTest;
import real.Student;

import java.util.Comparator;
import java.util.List;

/**
 * @Description: 操作分为惰性求值和及早求值，通过看操作的返回值，返回的Stream则为惰性，否则为及早；例如filter返回的就是Stream
 * @author: yjl
 * @date: 2024年02月08日 9:16
 */
public class OperationSort {
    public static void main(String[] args) {
        List<Student> students=StreamTest.init();
        students.stream().filter(o->{
            System.out.println(o.toString());
            return "zs".equals(o.getName());
        });
        students.stream().filter(o->{
            System.out.println(o.toString());
            return "zs".equals(o.getName());
        }).count();
        Student student=students.stream().max(Comparator.comparing(Student::getHeight)).orElse(null);
        System.out.println(student);

    }
}
