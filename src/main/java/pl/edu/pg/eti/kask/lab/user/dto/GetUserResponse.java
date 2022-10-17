package pl.edu.pg.eti.kask.lab.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private Long id;
    private String userName;
    private String email;
    private LocalDate birthDate;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
    }
}
