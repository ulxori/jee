package pl.edu.pg.eti.kask.lab.opinion.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDishOpinionsResponse {
    @Getter
    @Builder
    public static class Opinion {
        private Long id;
        //private User user;
        //private Dish dish;
        private String content;
    }

    @Singular
    private List<Opinion> opinions;

    public static Function<Collection<pl.edu.pg.eti.kask.lab.opinion.entity.Opinion>, GetDishOpinionsResponse> entityToDtoMapper() {
        return opinions -> {
            GetDishOpinionsResponseBuilder response = GetDishOpinionsResponse.builder();
            opinions.stream()
                    .map(opinion -> Opinion.builder()
                            .id(opinion.getId())
                            .content(opinion.getContent())
                            .build())
                    .forEach(response::opinion);
            return response.build();
        };
    }
}
