package pl.edu.pg.eti.kask.lab.opinion.repository;

import pl.edu.pg.eti.kask.lab.datastore.DataStore;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class OpinionRepository implements SimpleRepository<Opinion, Long> {

    private DataStore store;

    @Inject
    public OpinionRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Opinion> find(Long id) {
        return store.findOpinion(id);
    }

    @Override
    public List<Opinion> findAll() {
        return store.findAllOpinions();
    }

    @Override
    public void create(Opinion entity) {
        store.createOpinion(entity);
    }

    @Override
    public void delete(Opinion entity) {
        store.deleteOpinion(entity);
    }

    @Override
    public void update(Opinion entity) {
        store.updateOpinion(entity);
    }
}
