package pl.edu.pg.eti.kask.lab.dish.dtos;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDishOpinionRequest {
    private Long userId;
    private String content;
    public static Function<CreateDishOpinionRequest, Opinion> dtoToEntityMapper(
            Function<Long, User> userFunction, Supplier<Dish> dishSupplier) {
        return request -> Opinion.builder()
                .user(userFunction.apply(request.getUserId()))
                .dish(dishSupplier.get())
                .content(request.getContent())
                .build();
    }
}
