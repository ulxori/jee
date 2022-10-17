package pl.edu.pg.eti.kask.lab.datastore;

import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.utils.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataStore {
    private Set<User> users = new HashSet<>();

    public Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createUser(User user) {
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
}
