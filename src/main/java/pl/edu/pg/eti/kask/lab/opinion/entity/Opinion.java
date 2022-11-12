package pl.edu.pg.eti.kask.lab.opinion.entity;

import lombok.*;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "opinions")
public class Opinion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dishes")
    private Dish dish;

    private String content;
}
