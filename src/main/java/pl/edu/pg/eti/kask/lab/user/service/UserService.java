package pl.edu.pg.eti.kask.lab.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.entity.UserRoles;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
public class UserService {
    private UserRepository userRepository;
    private SecurityContext securityContext;
    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public UserService(UserRepository userRepository, SecurityContext securityContext, Pbkdf2PasswordHash pbkdf) {
        this.userRepository = userRepository;
        this.securityContext = securityContext;
        this.pbkdf = pbkdf;
    }

    public Optional<User> find(Long id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PermitAll
    public void create(User user) {
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            user.setRoles(List.of(UserRoles.USER));
        }
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    public User getCurrentUser() {
        return userRepository.find(1L).get();
    }
}

