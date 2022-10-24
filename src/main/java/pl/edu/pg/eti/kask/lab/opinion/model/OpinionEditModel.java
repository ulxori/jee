package pl.edu.pg.eti.kask.lab.opinion.model;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import javax.enterprise.inject.Model;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class OpinionEditModel {
    private Long id;
    private String content;
    private User user;
    private DishModel dish;

    public static Function<Opinion,OpinionEditModel> entityToModelMapper() {
        return opinion -> OpinionEditModel.builder()
                .content(opinion.getContent())
                .user(opinion.getUser())
                .dish(DishModel.entityToModelMapper().apply(opinion.getDish()))
                .id(opinion.getId())
                .build();
    }

    public static Function<OpinionEditModel, Opinion> modelToEntityMapper() {
       return model -> Opinion.builder()
               .content(model.getContent())
               .id(model.getId())
               .user(model.getUser())
               .dish(DishModel.modelToEntityMapper().apply(model.getDish()))
               .build();
    }
}
