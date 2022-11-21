package pl.edu.pg.eti.kask.lab.dish.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Log
@Dependent
public class DishRepository implements SimpleRepository<Dish, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Dish> find(Long id) {
        return Optional.ofNullable(em.find(Dish.class, id));
    }

    @Override
    public List<Dish> findAll() {
        return em.createQuery("select d from Dish d", Dish.class).getResultList();
    }

    @Override
    public void create(Dish entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Dish entity) {
        em.remove(em.find(Dish.class, entity.getId()));
    }

    @Override
    public void update(Dish entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Dish entity) {
        em.detach(entity);
    }
}
