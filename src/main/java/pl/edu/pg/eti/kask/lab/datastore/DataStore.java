package pl.edu.pg.eti.kask.lab.datastore;

import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.utils.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataStore {
    private Set<User> users = new HashSet<>();
    private Set<Dish> dishes = new HashSet<>();
    private Set<Opinion> opinions = new HashSet<>();

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User user) {
        user.setId(findAllUsers().stream()
                .mapToLong(User::getId)
                .max().orElse(0) + 1);

        users.stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findFirst()
                .ifPresentOrElse(original -> {
                    throw new IllegalArgumentException(
                            String.format("The given email %s is already taken", user.getEmail()));
                }, () -> users.add(CloningUtility.clone(user)));
    }

    public synchronized Optional<Opinion> findOpinion(Long id) {
        return opinions.stream()
                .filter(option-> option.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Opinion> findAllOpinions() {
        return opinions.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createOpinion(Opinion opinion) {
        opinion.setId(findAllOpinions().stream()
                .mapToLong(Opinion::getId)
                .max().orElse(0) + 1);

        opinions.add(opinion);
   }

   public synchronized void deleteOpinion(Opinion opinion) {
        findOpinion(opinion.getId()).ifPresent(opinion1 -> opinions.remove(opinion1));
   }

    public synchronized void updateOpinion(Opinion opinion) throws IllegalArgumentException {
        findOpinion(opinion.getId()).ifPresentOrElse(
                original -> {
                    opinions.remove(original);
                    opinions.add(CloningUtility.clone(opinion));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The opinion with id \"%d\" does not exist", opinion.getId()));
                });
    }

   public synchronized Optional<Dish> findDish(Long id) {
        return dishes.stream()
                .filter(dish -> dish.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Dish> findAllDishes() {
        return dishes.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createDish(Dish dish) {
        dish.setId(findAllDishes().stream()
                .mapToLong(Dish::getId)
                .max().orElse(0) + 1);

        dishes.add(dish);
   }

    public synchronized void updateDish(Dish dish) throws IllegalArgumentException {
        findDish(dish.getId()).ifPresentOrElse(
                original -> {
                    dishes.remove(original);
                    dishes.add(CloningUtility.clone(dish));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The dish with id \"%d\" does not exist", dish.getId()));
                });
    }

   public synchronized void deleteDish(Dish dish) {
        dishes.remove(dish);
   }

}
