package pl.edu.pg.eti.kask.lab.dish.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class DishService {
    private DishRepository dishRepository;


    @Inject
    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Optional<Dish> find(Long id) {
        return dishRepository.find(id);
    }

    @Transactional
    public void update(Dish dish) {
        Dish original = dishRepository.find(dish.getId()).orElseThrow();
        dishRepository.detach(original);
        dishRepository.update(dish);
    }

    @Transactional
    public void create(Dish dish) {
        dishRepository.create(dish);
    }

    @Transactional
    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }
}
