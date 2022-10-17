package pl.edu.pg.eti.kask.lab.portrait.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.portrait.entity.Portrait;
import pl.edu.pg.eti.kask.lab.portrait.repository.PortraitRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@NoArgsConstructor
@ApplicationScoped
public class PortraitService {

    private PortraitRepository portraitRepository;

    @Inject
    public PortraitService(PortraitRepository portraitRepository) {
        this.portraitRepository = portraitRepository;
    }

    public void addPortrait(Long id, InputStream is) {
        try {
            portraitRepository.create(new Portrait(id, is.readAllBytes()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    public void deletePortrait(Portrait portrait) {
        portraitRepository.delete(portrait);
    }
    public void updatePortrait(Long id, InputStream is) {
        try {
            portraitRepository.create(new Portrait(id, is.readAllBytes()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    public Optional<Portrait> getPortrait(Long id) {
        return portraitRepository.find(id);
    }
}
