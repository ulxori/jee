package pl.edu.pg.eti.kask.lab.opinion.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.repository.OpinionRepository;
import pl.edu.pg.eti.kask.lab.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class OpinionService {
    private OpinionRepository opinionRepository;
    private DishRepository dishRepository;
    private UserRepository userRepository;

    @Inject
    public OpinionService(OpinionRepository opinionRepository, DishRepository dishRepository,
                          UserRepository userRepository) {
        this.opinionRepository = opinionRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public List<Opinion> findAllForDish(Long dishId) {
        return opinionRepository.findForDish(dishId);
    }

    public Optional<Opinion> find(Long id) {
        return opinionRepository.find(id);
    }

    @Transactional
    public void update(Opinion opinion) {
        opinionRepository.update(opinion);
    }

    @Transactional
    public void create(Opinion opinion) {
        opinionRepository.create(opinion);
        dishRepository.find(opinion.getDish().getId()).ifPresent(dish -> dish.getOpinions().add(opinion));
        userRepository.find(opinion.getUser().getId()).ifPresent(user -> user.getOpinions().add(opinion));
    }

    @Transactional
    public void delete(Opinion opinion) {
        opinionRepository.delete(opinion);
    }
}
