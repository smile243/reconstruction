package real.processor;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 条件处理器
 */
@Slf4j
public class ConditionProcessor<T> {
    private final ConcurrentLinkedQueue<ConditionHandler<T>> handlers = new ConcurrentLinkedQueue<>();
    // 兜底处理器
    private Consumer<T> defaultHandler = t -> {};
    /**
     * 回滚处理器
     */
    static class RollbackHandler<T> {
        final long sequence;
        final Function<T, T> rollbackFunction; //回滚逻辑

        RollbackHandler(long sequence, Function<T, T> rollbackFunction) {
            this.sequence = sequence;
            this.rollbackFunction = rollbackFunction;
        }
    }

    /**
     * 条件处理器
     */
    static class ConditionHandler<T> {
        final int priority;
        final long sequence;
        final Predicate<T> predicate;
        final Function<T, T> function;
        final boolean stopOnMatch;
        final RollbackHandler<T> rollbackHandler;
        ConditionHandler(int priority, long sequence, Predicate<T> predicate,
                         Function<T, T> function, boolean stopOnMatch, RollbackHandler<T> rollbackHandler) {
            this.priority = priority;
            this.sequence = sequence;
            this.predicate = predicate;
            this.function = function;
            this.stopOnMatch = stopOnMatch;
            this.rollbackHandler = rollbackHandler;
        }
    }

    public static class Builder<T> {
        private final ConditionProcessor<T> processor = new ConditionProcessor<>();
        public Builder<T> defaultHandler(Consumer<T> handler) {
            processor.defaultHandler = handler;
            return this;
        }
    }

    /**
     * 根据已注册的条件处理器处理目标对象
     */
    public T process(T target) {
        T result = target;
        boolean handlerExecuted = false;
        List<ConditionHandler<T>> conditionHandlers = getSortedHandlers();
        for (ConditionHandler<T> handler : handlers) {
            try {
                if(handler.predicate.test(result)) {
                    T newResult = handler.function.apply(result);
                    if(newResult!=null) {
                        result = newResult;
                    }

                    handlerExecuted = true;
                    if(handler.stopOnMatch) {
                        break;
                    }
                }
            } catch (Exception e) {
                log.error("处理器出现异常:[{}]，准备执行回滚",e.getMessage());
                return result;
            }
        }
        if(!handlerExecuted) {
            defaultHandler.accept(result);
        }
        return result;
    }

    private List<ConditionHandler<T>> getSortedHandlers() {
        List<ConditionHandler<T>> conditionHandlers = new ArrayList<>(handlers);
        conditionHandlers.sort(Comparator.comparingLong(h->h.sequence));
        return conditionHandlers;
    }
}
