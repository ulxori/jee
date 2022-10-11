import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello world");
        User user = User.builder()
                .id(1l)
                .userName("xd")
                .email("fds")
                .password("sdf")
                .build();

        User user2 = new User(2l);
        User user3 = new User(3l,"h","h","h",null);
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);
        Dish dish = Dish.builder().build();
        Opinion opinion = Opinion.builder().build();
        Opinion opinion1 = new Opinion(1l, user2);
        System.out.println(opinion1);
    }
}
