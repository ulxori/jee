package pl.edu.pg.eti.kask.lab.opinion.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.dtos.CreateDishOpinionRequest;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishOpinionRequest {
    private String content;
    public static BiFunction<Opinion, UpdateDishOpinionRequest, Opinion> dtoToEntityMapper() {
        return (opinion, request) -> {
            opinion.setContent(request.getContent());
            return opinion;
        };
    }
}

