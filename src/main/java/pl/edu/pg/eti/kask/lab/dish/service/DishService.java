package pl.edu.pg.eti.kask.lab.dish.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;
import pl.edu.pg.eti.kask.lab.opinion.repository.OpinionRepository;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class DishService {
    private DishRepository dishRepository;
    private OpinionService opinionService;


    @Inject
    public DishService(DishRepository dishRepository, OpinionService opinionService) {
        this.dishRepository = dishRepository;
        this.opinionService = opinionService;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Optional<Dish> find(Long id) {
        return dishRepository.find(id);
    }

    @Transactional
    public void update(Dish dish) {
        dishRepository.update(dish);
    }

    @Transactional
    public void create(Dish dish) {
        dishRepository.create(dish);
    }

    @Transactional
    public void delete(Dish dish) {
        //opinionService.findAllForDish(dish.getId()).forEach(opinionService::delete);
        dishRepository.delete(dish);
    }
}
