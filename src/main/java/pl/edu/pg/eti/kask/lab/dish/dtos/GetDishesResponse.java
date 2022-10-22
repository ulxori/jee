package pl.edu.pg.eti.kask.lab.dish.dtos;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDishesResponse {
    @Getter
    @Builder
    public static class Dish {
        private Long id;
        private String name;
    }

    @Singular
    private List<Dish> dishes;

    public static Function<Collection<pl.edu.pg.eti.kask.lab.dish.entity.Dish>, GetDishesResponse> entityToDtoMapper() {
       return dishes -> {
            GetDishesResponseBuilder response = GetDishesResponse.builder();
            dishes.stream()
                    .map(character -> Dish.builder()
                            .id(character.getId())
                            .name(character.getName())
                            .build())
                    .forEach(response::dish);
            return response.build();
        };
    }
}
