package pl.edu.pg.eti.kask.lab.opinion.service;

import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.repository.OpinionRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OpinionService {
    private OpinionRepository opinionRepository;
    private DishRepository dishRepository;

    @Inject
    public OpinionService(OpinionRepository opinionRepository, DishRepository dishRepository) {
        this.opinionRepository = opinionRepository;
        this.dishRepository = dishRepository;
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public List<Opinion> findAllForDish(Long dishId) {
       return findAll().stream()
               .filter(opinion -> opinion.getDish().getId().equals(dishId))
               .collect(Collectors.toList());
    }

    public Optional<Opinion> find(Long id) {
        Optional<Dish> dish = dishRepository.find(id);
        if (dish.isEmpty()) {
            return Optional.empty();
        }
        return opinionRepository.find(id);
    }

    public void update(Opinion opinion) {
        opinionRepository.update(opinion);
    }

    public void create(Opinion opinion) {
        opinionRepository.create(opinion);
    }
    public void delete(Opinion opinion) {
        opinionRepository.delete(opinion);
    }
}
