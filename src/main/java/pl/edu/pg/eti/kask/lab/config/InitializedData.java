package pl.edu.pg.eti.kask.lab.config;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.service.UserService;
import pl.edu.pg.eti.kask.lab.utils.Sha256Utility;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@ApplicationScoped
@NoArgsConstructor
public class InitializedData {
    private UserService userService;
    private DishService dishService;
    private OpinionService opinionService;
    private Pbkdf2PasswordHash pbkdf;
    //private RequestContextController requestContextController;

    @Inject
    public void setPbkdf(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @EJB
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    /*@Inject
    public InitializedData(UserService userService, DishService dishService,
                           OpinionService opinionService, RequestContextController requestContextController) {
        this.userService= userService;
        this.dishService = dishService;
        this.opinionService = opinionService;
        this.requestContextController = requestContextController;
    }*/

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        initData();
    }

    public synchronized void initData() {
        //requestContextController.activate();

        User user1 = User.builder()
                .password(pbkdf.generate("admin".toCharArray()))
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
                .forEach(userService::create);

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
                .forEach(dishService::create);

        Opinion opinion = Opinion.builder()
                .content("abc")
                .dish(dish1)
                .user(user1)
                .build();

        Opinion opinion2 = Opinion.builder()
                .content("xyz")
                .dish(dish1)
                .user(user1)
                .build();

        Opinion opinion3 = Opinion.builder()
                .content("cde")
                .dish(dish1)
                .user(user1)
                .build();

        Opinion opinion4 = Opinion.builder()
                .content("efg")
                .dish(dish1)
                .user(user1)
                .build();

        Stream.of(opinion4, opinion3, opinion2, opinion)
                .forEach(opinionService::create);

        //requestContextController.deactivate();
    }

}
