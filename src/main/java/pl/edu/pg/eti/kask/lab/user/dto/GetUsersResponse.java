package pl.edu.pg.eti.kask.lab.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class GetUsersResponse {
    @Singular
    private List<User> users;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {
        private Long id;
        private String userName;
    }

    public static Function<List<pl.edu.pg.eti.kask.lab.user.entity.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder responseBuilder = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .userName(user.getUserName())
                            .build())
                    .forEach(responseBuilder::user);
            return responseBuilder.build();
        };
    }
}
