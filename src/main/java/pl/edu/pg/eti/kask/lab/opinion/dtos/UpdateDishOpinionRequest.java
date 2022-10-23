package pl.edu.pg.eti.kask.lab.opinion.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishOpinionRequest {
    private Long id;
    //private User user;
   // private Dish dish;

    /*private String content;    public static BiFunction<Dish, pl.edu.pg.eti.kask.lab.dish.dtos.UpdateDishRequest, Dish> dtoToEntityMapper() {
        return (dish, request) -> {
            dish.setName(request.getName());
            dish.setPrice(request.getPrice());
            dish.setVegan(request.isVegan());
            return dish;
        };
    }*/
}

