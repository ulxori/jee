package pl.edu.pg.eti.kask.lab.dish.entity;


import lombok.*;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "dishes")
public class Dish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private boolean isVegan;
    private BigDecimal price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "dish", cascade = CascadeType.REMOVE)
    private List<Opinion> opinions;
}
