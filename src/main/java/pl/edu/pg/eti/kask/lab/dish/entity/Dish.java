package pl.edu.pg.eti.kask.lab.dish.entity;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Dish {
    private final Long id;
    private String name;
    private boolean isVegan;
    private BigDecimal price;
}
