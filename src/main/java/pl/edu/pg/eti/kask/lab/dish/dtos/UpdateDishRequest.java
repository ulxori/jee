package pl.edu.pg.eti.kask.lab.dish.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishRequest {
    private Long id;
    private String name;
    private boolean isVegan;
    private BigDecimal price;

    public static BiFunction<Dish, UpdateDishRequest, Dish> dtoToEntityMapper() {
        return (dish, request) -> {
            dish.setName(request.getName());
            dish.setPrice(request.getPrice());
            dish.setVegan(request.isVegan());
             return dish;
        };
    }
}
