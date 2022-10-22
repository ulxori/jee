package pl.edu.pg.eti.kask.lab.config;

import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;
import pl.edu.pg.eti.kask.lab.utils.Sha256Utility;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@ApplicationScoped
public class InitializedData {
    private UserRepository userRepository;
    private DishRepository dishRepository;
    @Inject
    public InitializedData(UserRepository userRepository, DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
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

        Dish dish1 = Dish.builder()
                .id(1L)
                .name("Zupa pomidorowa")
                .price(new BigDecimal("10.00"))
                .isVegan(true)
                .build();

        Dish dish2 = Dish.builder()
                .id(2L)
                .name("Tunczyk")
                .price(new BigDecimal("20.00"))
                .isVegan(false)
                .build();

        Dish dish3 = Dish.builder()
                .id(3L)
                .name("Zapiekanka")
                .price(new BigDecimal("30.00"))
                .isVegan(false)
                .build();

        Dish dish4 = Dish.builder()
                .id(4L)
                .name("Pizza")
                .price(new BigDecimal("40.00"))
                .isVegan(false)
                .build();

        Stream.of(dish1, dish2, dish3, dish4)
                .forEach(dishRepository::create);
    }

}
