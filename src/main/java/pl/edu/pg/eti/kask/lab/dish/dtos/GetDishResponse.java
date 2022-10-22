package pl.edu.pg.eti.kask.lab.dish.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GetDishResponse {
    private Long id;
    private String name;
    private boolean isVegan;
    private BigDecimal price;

    public static Function<Dish, GetDishResponse> entityToDtoMapper() {
        return dish -> GetDishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .isVegan(dish.isVegan())
                .price(dish.getPrice())
                .build();
    }
}
