package pl.edu.pg.eti.kask.lab.user.repository;

import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepository implements SimpleRepository<User, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(Long id) {
//        System.out.println("User repository" + em.find(User.class,id) + " opinions "+ em.getReference(User.class,id).getOpinions().size());
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }
}
