package pl.edu.pg.eti.kask.lab.user.repository;

import pl.edu.pg.eti.kask.lab.datastore.DataStore;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;
import pl.edu.pg.eti.kask.lab.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements SimpleRepository<User, Long> {
    private DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<User> find(Long id) {
        return dataStore.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    @Override
    public void create(User entity) {
        dataStore.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
