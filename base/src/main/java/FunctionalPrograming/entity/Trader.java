package FunctionalPrograming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:
 * @author: yjl
 * @date: 2024年05月23日 10:00
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Trader {
    private final String name;
    private final String city;
}
