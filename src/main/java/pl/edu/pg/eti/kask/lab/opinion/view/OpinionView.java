package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionViewModel;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
public class OpinionView {
    private final OpinionService opinionService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private OpinionViewModel opinionModel;

    @Inject
    public OpinionView(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    public void init() throws IOException {
        Optional<Opinion> opinion = opinionService.find(id);
        if (opinion.isPresent()) {
            this.opinionModel = OpinionViewModel.entityToModelMapper().apply(opinion.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Dish not found");
        }
    }
}
