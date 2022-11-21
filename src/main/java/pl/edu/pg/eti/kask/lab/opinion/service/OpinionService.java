package pl.edu.pg.eti.kask.lab.opinion.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.repository.OpinionRepository;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.entity.UserRoles;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
public class OpinionService {
    private OpinionRepository opinionRepository;
    private DishRepository dishRepository;
    private UserRepository userRepository;
    private SecurityContext securityContext;

    @Inject
    public OpinionService(OpinionRepository opinionRepository, DishRepository dishRepository,
                          UserRepository userRepository, SecurityContext securityContext) {
        this.opinionRepository = opinionRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public List<Opinion> findAllForDish(Long dishId) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return opinionRepository.findForDish(dishId);
        }
        return opinionRepository.findAllForDishAndUser(dishId, securityContext.getCallerPrincipal().getName());
    }

    public Optional<Opinion> findForDish(Long dishId, Long opinionId) {
        Optional<Opinion> opinion = opinionRepository.findForDish(dishId, opinionId);
        if (!canAccess(opinion)) {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
        return opinion;
    }

    public Optional<Opinion> find(Long id) {
        Optional<Opinion> opinion = opinionRepository.find(id);
        if (!canAccess(opinion)) {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
        return opinion;
    }

    public void update(Opinion opinion) {
        if (!canAccess(Optional.of(opinion))) {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
        Opinion original = opinionRepository.find(opinion.getId()).orElseThrow();
        opinionRepository.detach(original);
        opinionRepository.update(opinion);
    }

    public boolean create(Opinion opinion) {
        Optional<User> caller = userRepository.findByUserName(securityContext.getCallerPrincipal().getName());
        if (caller.isEmpty()) {
           return false;
        }
        opinion.setUser(caller.get());
        opinionRepository.create(opinion);
        dishRepository.find(opinion.getDish().getId()).ifPresent(dish -> dish.getOpinions().add(opinion));
        userRepository.find(opinion.getUser().getId()).ifPresent(user -> user.getOpinions().add(opinion));
        return true;
    }

    public void delete(Optional<Opinion> opinion) {
        if (!canAccess(opinion)) {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
        if (opinion.isPresent()) {
            opinion.get().getDish().getOpinions().remove(opinion.get());
            opinion.get().getUser().getOpinions().remove(opinion.get());
            opinionRepository.delete(opinion.get());
        }
    }

    private boolean canAccess(Optional<Opinion> opinion) {
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            if (opinion.isPresent()) {
                return opinion.get().getUser().getUserName().equals(securityContext.getCallerPrincipal().getName());
            }
        }
        return true;
    }
}
