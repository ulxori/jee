package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionEditModel;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;

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
public class OpinionEdit implements Serializable {
    private final OpinionService opinionService;
    private final DishService dishService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private OpinionEditModel opinion;

    @Getter
    private List<DishModel> dishes;

    @Inject
    public OpinionEdit(OpinionService opinionService, DishService dishService) {
        this.opinionService = opinionService;
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
