package FunctionalPrograming;


import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * 
 *
 * @author &{USER}
 * @date 2022/2/2520:48
 */
public class DifferentForm {
    public static void main(String[] args) {
        Runnable noArguments=()-> System.out.println("yjl");
        ActionListener oneArgument=event-> System.out.println("yjl");
        Runnable mutiStatement=()->{
            System.out.println("y");
            System.out.println("jl");
        };
        BinaryOperator<Long> add=(x,y)->x+y;
        BinaryOperator<Long> addExplicit=(Long x,Long y)->x+y;
    }
}
