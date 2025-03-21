package mapstruct;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @author: yjl
 * @date: 2024年04月28日 13:45
 */
@ToString
@Getter
@Setter
public class CarDTO {
    private String brand;
    private BigDecimal amount;
    private List<String> wheels;
    private int sort;
}
