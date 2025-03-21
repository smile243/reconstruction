package FunctionalPrograming;

import FunctionalPrograming.entity.Trader;
import FunctionalPrograming.entity.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Description:
 * @author: yjl
 * @date: 2024年05月23日 10:02
 */
public class java8 {
    public static void main(String[] args) {
        //test1();
        test2();
    }

    /**
     * 构建勾股流
     */
    public static void test2(){
        Stream<double []> pythagoreanTriples2 = IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(a->
                        IntStream.rangeClosed(a,100)
                                .mapToObj(b->new double[]{a,b,Math.sqrt(a*a+b*b)})
                                .filter(t->t[2]%1==0));
        pythagoreanTriples2.limit(2).forEach(t ->
                System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    public static void test1(){
        List<Transaction> list = Transaction.init();
        /*
        (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        (2) 交易员都在哪些不同的城市工作过？
        (3) 查找所有来自于剑桥的交易员，并按姓名排序。
        (4) 返回所有交易员的姓名字符串，按字母顺序排序。
        (5) 有没有交易员是在米兰工作的？
        (6) 打印生活在剑桥的交易员的所有交易额。
        (7) 所有交易中，最高的交易额是多少？
        (8) 找到交易额最小的交易。
         */
        List<Transaction> list1=list.stream()
                .filter(o->2011==o.getYear())
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(toList());
        List<String> list2=list.stream()
                .map(o->o.getTrader().getCity())
                .distinct()
                .collect(toList());
        List<Trader> list3=list.stream()
                .map(Transaction::getTrader)
                .filter(o->"Cambridge".equals(o.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toList());
        List<String> list4=list.stream()
                .map(o->o.getTrader().getName().split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .sorted().collect(toList());
        boolean list5=list.stream()
                .map(Transaction::getTrader)
                .anyMatch(o->"Milan".equals(o.getCity()));
        Integer list6=list.stream()
                .filter(o->"Cambridge".equals(o.getTrader().getCity()))
                .mapToInt(Transaction::getValue)
                .reduce(0,Integer::sum);
        String.join("123","456");
        Integer list7=list.stream()
                .mapToInt(Transaction::getValue).reduce(Integer::max).orElse(0);
        Integer list8=list.stream()
                .mapToInt(Transaction::getValue).reduce(Integer::min).orElse(0);;
        System.out.println("结束");
    }
}
