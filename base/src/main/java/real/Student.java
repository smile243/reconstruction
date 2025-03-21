package real;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年10月19日 13:36
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Student {
    @Builder.Default
    private String name="zs";
    private Integer age;
    private List<Integer> list;
    private BigDecimal height;
    private BigDecimal weight;
}
