package pl.edu.pg.eti.kask.lab.dish.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDishRequest {
    private String name;
    private boolean isVegan;
    private BigDecimal price;

    public static Function<CreateDishRequest, Dish> dtoToEntityMapper() {
        return request -> Dish.builder()
                .isVegan(request.isVegan)
                .price(request.price)
                .name(request.name)
                .build();
    }
}
