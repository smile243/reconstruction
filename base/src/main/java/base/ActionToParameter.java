package base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**行为参数化
 * 好处在于我们可以把迭代要筛选的集合的逻辑与对集合中每个元素应用的行为区分开来，这样我们可以重复使用一个方法，达到不同行为实现不同的目的。
 * @author yjl
 * @since 2024/5/31
 */
public class ActionToParameter {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Apple {
        private int weight;
        private String color;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**基础行为抽象
     * @author yjl
     * @since 2024/5/31
     */
    interface ApplePredicate {
        boolean test(Apple a);
    }

    static class AppleWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    static List<Apple> appleFactory(){
        List<Apple> inventory = new ArrayList<>();
        Apple appleA=new Apple(10,"green");
        Apple appleB=new Apple(15,"red");
        Apple appleC=new Apple(400,"green");
        inventory.add(appleA);
        inventory.add(appleB);
        inventory.add(appleC);
        return inventory;
    }

    public static void main(String[] args) {
        List<Apple> inventory = appleFactory();
        //List<Apple> result = filterApples(inventory, apple->"green".equals(apple.getColor()));
        List<Apple> result = filterApples(inventory, new AppleColorPredicate());
        result = filterApples(result, new AppleWeightPredicate());
        result.forEach(System.out::println);
    }
}
