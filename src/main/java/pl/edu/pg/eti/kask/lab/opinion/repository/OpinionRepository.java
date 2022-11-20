package pl.edu.pg.eti.kask.lab.opinion.repository;

import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Dependent
public class OpinionRepository implements SimpleRepository<Opinion, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Opinion> find(Long id) {
        System.out.println("OpinionRepository: "+em.find(Opinion.class, id));
        return Optional.ofNullable(em.find(Opinion.class, id));
    }

    @Override
    public List<Opinion> findAll() {
        return em.createQuery("select o from Opinion o", Opinion.class).getResultList();
    }

    @Override
    public void create(Opinion entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Opinion entity) {
        em.remove(em.find(Opinion.class, entity.getId()));
    }

    @Override
    public void update(Opinion entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Opinion entity) {
        em.detach(entity);
    }

    public List<Opinion> findForDish(Long dishId) {
        return em.createQuery("select o from Opinion o where o.dish.id = :dish", Opinion.class)
                .setParameter("dish", dishId)
                .getResultList();
    }

    public List<Opinion> findAllForDishAndUser(Long dishId, String userName) {
        return em.createQuery("select o from Opinion o where o.dish.id = :dish and o.user.userName = :user", Opinion.class)
                .setParameter("dish", dishId)
                .setParameter("user", userName)
                .getResultList();
    }
}
