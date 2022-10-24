package pl.edu.pg.eti.kask.lab.dish.model;

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
public class DishModel {
    private Long id;
    private String name;
    private boolean isVegan;
    private BigDecimal price;

    public static Function<Dish, DishModel> entityToModelMapper() {
        return dish -> DishModel.builder()
                .id(dish.getId())
                .name(dish.getName())
                .isVegan(dish.isVegan())
                .price(dish.getPrice())
                .build();
    }

    public static Function<DishModel, Dish> modelToEntityMapper() {
        return dish -> Dish.builder()
                .id(dish.getId())
                .name(dish.getName())
                .isVegan(dish.isVegan())
                .price(dish.getPrice())
                .build();
    }
}
