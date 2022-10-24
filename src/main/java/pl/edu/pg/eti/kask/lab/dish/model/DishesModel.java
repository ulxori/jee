package pl.edu.pg.eti.kask.lab.dish.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import pl.edu.pg.eti.kask.lab.dish.dtos.GetDishesResponse;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@Setter
public class DishesModel {

    @Builder
    @Getter
    @Setter
    public static class Dish {
        private Long id;
        private String name;
    }

    @Singular
    private List<Dish> dishes;

    public static Function<Collection<pl.edu.pg.eti.kask.lab.dish.entity.Dish>, DishesModel> entityToDtoMapper() {
        return dishes -> {
            DishesModel.DishesModelBuilder model = DishesModel.builder();
            dishes.stream()
                    .map(dish -> DishesModel.Dish.builder()
                            .id(dish.getId())
                            .name(dish.getName())
                            .build())
                    .forEach(model::dish);
            return model.build();
        };
    }
}
