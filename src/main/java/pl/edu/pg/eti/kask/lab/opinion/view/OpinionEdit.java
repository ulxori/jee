package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionEditModel;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ViewScoped
@Named
@NoArgsConstructor
public class OpinionEdit implements Serializable {
    private OpinionService opinionService;
    private DishService dishService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private OpinionEditModel opinion;

    @Getter
    private List<DishModel> dishes;

    @EJB
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public void init() throws IOException {
        Optional<Opinion> opinion = opinionService.find(id);
        if (opinion.isPresent()) {
            this.opinion = OpinionEditModel.entityToModelMapper().apply(opinion.get());
            this.dishes = dishService.findAll().stream()
                    .map(DishModel.entityToModelMapper())
                    .collect(Collectors.toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Opinion not found");
        }
    }

    public String saveAction() {
        opinionService.update(OpinionEditModel.modelToEntityMapper().apply(opinion));
        return null;
    }
}
