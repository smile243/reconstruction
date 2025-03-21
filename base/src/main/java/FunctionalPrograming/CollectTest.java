package FunctionalPrograming;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 
 * @author: yjl
 * @date: 2021年11月11日 8:56
 */
public class CollectTest {
    @Test
    public static void main(String[] args) {
        List<String> collected= Stream.of("a","b","c").collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("a","b","c"),collected);
    }
}
