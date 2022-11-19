package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionViewModel;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
@NoArgsConstructor
public class OpinionView {
    private OpinionService opinionService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private OpinionViewModel opinionModel;

    @EJB
    public void setOpinionService(OpinionService opinionService) {
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
