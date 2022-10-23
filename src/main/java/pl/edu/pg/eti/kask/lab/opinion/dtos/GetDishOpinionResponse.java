package pl.edu.pg.eti.kask.lab.opinion.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.dtos.GetDishResponse;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.math.BigDecimal;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GetDishOpinionResponse {
    private Long id;
    //private User user;
    //private Dish dish;
    private String content;

    public static Function<Opinion, GetDishOpinionResponse> entityToDtoMapper() {
        return dish -> GetDishOpinionResponse.builder()
                .id(dish.getId())
                .content(dish.getContent())
                .build();
    }
}
