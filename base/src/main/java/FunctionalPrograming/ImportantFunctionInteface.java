package FunctionalPrograming;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * java8中重要的函数接口
 * Predicate
 * Consumer
 * Function
 * Supplier
 * UnaryOperator
 * BinaryOperator
 * @author &{USER}
 * @date 2022/2/2521:43
 */
public class ImportantFunctionInteface {
    public static void main(String[] args) {
        ImportantFunctionInteface test=new ImportantFunctionInteface();
        System.out.println(test.PredicateTest(5));
        System.out.println(test.BinaryOperatorTest(5l,7l));
    }

    public boolean PredicateTest(int y){
        Predicate<Integer> atLeast5=x->x>5;
        return atLeast5.test(y);
    }

    public Long BinaryOperatorTest(Long a,Long b){
        BinaryOperator<Long> add=(x,y)->x+y;
        return add.apply(a,b);
    }
}
