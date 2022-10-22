package pl.edu.pg.eti.kask.lab.dish.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.repository.DishRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class DishService {
    private DishRepository dishRepository;

    @Inject
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Optional<Dish> find(Long id) {
        return dishRepository.find(id);
    }

    public void update(Dish dish) {
        dishRepository.update(dish);
    }

    public void create(Dish dish) {
        dishRepository.create(dish);
    }
    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }
}
