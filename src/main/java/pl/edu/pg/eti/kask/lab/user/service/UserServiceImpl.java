package pl.edu.pg.eti.kask.lab.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> find(Long id) {
        return userRepository.find(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser() {
        return userRepository.find(1L).get();
    }
}

