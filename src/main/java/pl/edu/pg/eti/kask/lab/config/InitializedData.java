package pl.edu.pg.eti.kask.lab.config;

import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.entity.UserRoles;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Singleton
@Startup
public class InitializedData {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public InitializedData(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    public InitializedData() {
    }

    @PostConstruct
    public synchronized void initData() {
        User user1 = User.builder()
                .password(pbkdf.generate("abc".toCharArray()))
                .email("user1@gmail.com")
                .userName("user1")
                .birthDate(LocalDate.of(2000,1,1))
                .roles(List.of(UserRoles.USER))
                .build();

        User user2 = User.builder()
                .password(pbkdf.generate("admin".toCharArray()))
                .email("admin2@gmail.com")
                .userName("admin")
                .birthDate(LocalDate.of(2000,1,1))
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();

        User user3 = User.builder()
                .password(pbkdf.generate("abc".toCharArray()))
                .email("admin2@gmail.com")
                .email("user3@gmail.com")
                .userName("user3")
                .birthDate(LocalDate.of(2000,1,1))
                .roles(List.of(UserRoles.USER))
                .build();

        User user4 = User.builder()
                .password(pbkdf.generate("abc".toCharArray()))
                .email("admin2@gmail.com")
                .email("user4@gmail.com")
                .userName("user4")
                .birthDate(LocalDate.of(2000,1,1))
                .roles(List.of(UserRoles.USER))
                .build();

        Stream.of(user1, user2, user3, user4)
                .forEach(em::persist);

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
                .forEach(em::persist);

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
                .user(user2)
                .build();

        Opinion opinion4 = Opinion.builder()
                .content("efg")
                .dish(dish1)
                .user(user3)
                .build();

        Stream.of(opinion4, opinion3, opinion2, opinion)
                .forEach(em::persist);

        user1.setOpinions(List.of(opinion, opinion2));
        user2.setOpinions(List.of(opinion3));
        user3.setOpinions(List.of(opinion4));

        dish1.setOpinions(List.of(opinion, opinion2, opinion3, opinion4));
    }

}
