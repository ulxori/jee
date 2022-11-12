package pl.edu.pg.eti.kask.lab.repository;

import java.util.List;
import java.util.Optional;


public interface SimpleRepository<E, K> {

    Optional<E> find(K id);
    List<E> findAll();
    void create(E entity);
    void delete(E entity);
    void update(E entity);
    void detach(E entity);
}
