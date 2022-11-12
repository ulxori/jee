package pl.edu.pg.eti.kask.lab.opinion.repository;

import pl.edu.pg.eti.kask.lab.datastore.DataStore;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class OpinionRepository implements SimpleRepository<Opinion, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Opinion> find(Long id) {
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
}
