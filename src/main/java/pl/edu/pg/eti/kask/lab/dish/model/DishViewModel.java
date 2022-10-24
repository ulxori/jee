package pl.edu.pg.eti.kask.lab.dish.model;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DishViewModel {

    private String name;
    private boolean vegan;
    private BigDecimal price;
    private List<Opinion> opinions;

    public static BiFunction<Dish, List<Opinion>, DishViewModel> entityToModelMapper() {
        return (dish, opinions) -> DishViewModel.builder()
                .name(dish.getName())
                .vegan(dish.isVegan())
                .price(dish.getPrice())
                .opinions(opinions)
                .build();
    }

    public static BiFunction<Dish, DishViewModel, Dish> modelToEntityUpdater() {
        return (dish, request) -> {
            dish.setName(request.getName());
            dish.setVegan(request.isVegan());
            dish.setPrice(request.getPrice());
            return dish;
        };
    }

}
