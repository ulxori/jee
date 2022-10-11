package pl.edu.pg.eti.kask.lab.opinion.entity;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.user.entity.User;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Opinion {
    private final Long id;
    private final User user;
    private Dish dish;
    private String content;
}
