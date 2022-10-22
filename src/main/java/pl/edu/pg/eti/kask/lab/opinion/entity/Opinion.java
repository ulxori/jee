package pl.edu.pg.eti.kask.lab.opinion.entity;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.io.Serializable;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Opinion implements Serializable {
    private Long id;
    private User user;
    private Dish dish;
    private String content;
}
