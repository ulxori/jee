package pl.edu.pg.eti.kask.lab.user.repository;

import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements SimpleRepository<User, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByUserName(String userName) {
        return Optional.of(em.createQuery("select u from User u where u.userName = :user", User.class)
                .setParameter("user", userName)
                .getSingleResult());
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
