package pl.edu.pg.eti.kask.lab.dish.repository;

import pl.edu.pg.eti.kask.lab.datastore.DataStore;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class DishRepository implements SimpleRepository<Dish, Long> {

    private DataStore dataStore;

    @Inject
    public DishRepository(DataStore store) {
        this.dataStore = store;
    }

    @Override
    public Optional<Dish> find(Long id) {
        return dataStore.findDish(id);
    }

    @Override
    public List<Dish> findAll() {
        return dataStore.findAllDishes();
    }

    @Override
    public void create(Dish entity) {
        dataStore.createDish(entity);
    }

    @Override
    public void delete(Dish entity) {
        dataStore.deleteDish(entity);
    }

    @Override
    public void update(Dish entity) {
        dataStore.updateDish(entity);
    }
}
