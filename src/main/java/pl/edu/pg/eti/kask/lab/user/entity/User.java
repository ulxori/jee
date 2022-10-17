package pl.edu.pg.eti.kask.lab.user.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Long id;
    private String userName;

    @ToString.Exclude
    private String password;
    private String email;
    private LocalDate birthDate;
}
