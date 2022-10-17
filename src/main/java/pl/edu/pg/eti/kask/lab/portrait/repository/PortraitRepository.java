package pl.edu.pg.eti.kask.lab.portrait.repository;

import pl.edu.pg.eti.kask.lab.portrait.entity.Portrait;
import pl.edu.pg.eti.kask.lab.portrait.manager.PortraitManager;
import pl.edu.pg.eti.kask.lab.repository.SimpleRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class PortraitRepository implements SimpleRepository<Portrait, Long> {

    private PortraitManager portraitManager;

    @Inject
    public PortraitRepository(PortraitManager portraitManager) {
        this.portraitManager = portraitManager;
    }

    @Override
    public Optional<Portrait> find(Long id) {
        return portraitManager.getPortrait(id);
    }

    @Override
    public List<Portrait> findAll() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void create(Portrait entity) {
        portraitManager.addPortrait(entity);
    }

    @Override
    public void delete(Portrait entity) {
        portraitManager.deletePortrait(entity);
    }

    @Override
    public void update(Portrait entity) {
        portraitManager.updatePortrait(entity);
    }

}
