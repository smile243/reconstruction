package base;

import java.util.Iterator;

public interface YjlIterator<E> extends Iterator<E> {
    /**
     * @Description: 是否有下个元素
     * @param loopTime 循环次数
     * @author: yjl
     * @date: 2021/10/21
     */

    boolean hasNext(int loopTime);

    E next(int loopTime);
}
