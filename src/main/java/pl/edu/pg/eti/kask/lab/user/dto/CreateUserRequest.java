package pl.edu.pg.eti.kask.lab.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.entity.UserRoles;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

   private String userName;

   private String password;

   private String email;

   private LocalDate birthDate;

   public static Function<CreateUserRequest, User> dtoToEntityMapper() {
       return request -> User.builder()
               .password(request.getPassword())
               .email(request.getEmail())
               .roles(List.of(UserRoles.USER))
               .birthDate(request.getBirthDate())
               .userName(request.getUserName())
               .build();
   }
}
