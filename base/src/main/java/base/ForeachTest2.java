package base;

/**
 * @Description: 魔鬼中的魔鬼.Itr内的hasNext()使用curl!=size而不用cur<size为了防止fail-fast机制失效
 * @author: yjl
 * @date: 2021年10月21日 13:33
 */
public class ForeachTest2 {
    public static void main(String[] args) {
        YjlArrayList<String> list = new YjlArrayList<>();
        list.add("公众号");
        list.add("why");
        YjlIterator<String> var = list.iterator();
        int loopTime = 1;
        while (var.hasNext(loopTime)) {
            String item = var.next(loopTime);
            if ("why".equals(item)) {
                list.remove(item);
            }
            loopTime++;
        }
    }
}
