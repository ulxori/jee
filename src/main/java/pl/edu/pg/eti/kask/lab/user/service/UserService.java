package pl.edu.pg.eti.kask.lab.user.service;

import pl.edu.pg.eti.kask.lab.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> find(Long id);
    List<User> findAll();

    User getCurrentUser();
}
