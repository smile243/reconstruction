import cn.hutool.core.date.format.FastDateFormat;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleDateFormatTest {
    private final AtomicBoolean STOP = new AtomicBoolean();
    // 非线程安全 里面的NumberFormat非线性安全，是个有状态的成员变量
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-M-d");
    //solution1
    private final ThreadLocal<SimpleDateFormat> TLFORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-M-d"));
    //solution2
    private final FastDateFormat FASTFORMATTER = FastDateFormat.getInstance("yyyy-M-d");

    @Test
    public void concurrenceTest() {
        Runnable runnable = () -> {
            int count = 0;
            while (!STOP.get()) {
                try {
                    //FORMATTER.parse("2023-7-15");
                    //TLFORMATTER.get().parse("2023-7-15");
                    FASTFORMATTER.parse("2023-7-15");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (++count > 3) {
                        STOP.set(true);
                    }
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
