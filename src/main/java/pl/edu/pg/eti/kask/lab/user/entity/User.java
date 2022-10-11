package pl.edu.pg.eti.kask.lab.user.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private final Long id;
    private String userName;

    @ToString.Exclude
    private String password;
    private String email;
    private LocalDate birthDate;
}
