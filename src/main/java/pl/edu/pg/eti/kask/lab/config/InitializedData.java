package pl.edu.pg.eti.kask.lab.config;

import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;
import pl.edu.pg.eti.kask.lab.utils.Sha256Utility;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.stream.Stream;

@ApplicationScoped
public class InitializedData {
    private UserRepository userRepository;

    @Inject
    public InitializedData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        initData();
    }

    public synchronized void initData() {
        User user1 = User.builder()
                .password(Sha256Utility.hash("abc"))
                .email("user1@gmail.com")
                .userName("user1")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        User user2 = User.builder()
                .password(Sha256Utility.hash("abc"))
                .email("user2@gmail.com")
                .userName("user2")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        User user3 = User.builder()
                .password(Sha256Utility.hash("abc"))
                .email("user3@gmail.com")
                .userName("user3")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        User user4 = User.builder()
                .password(Sha256Utility.hash("abc"))
                .email("user4@gmail.com")
                .userName("user4")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        Stream.of(user1, user2, user3, user4)
                .forEach(userRepository::create);
    }

}
