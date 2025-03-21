package dynamic_compilation;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年07月15日 17:43
 */
public class Supermarket implements Store{
    @Override
    public void sell() {
        System.out.println("invoke supermarket sell method");
    }
}
