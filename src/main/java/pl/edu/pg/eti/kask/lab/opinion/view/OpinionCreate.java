package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionEditModel;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class OpinionCreate implements Serializable {
    private final OpinionService opinionService;
    private final DishService dishService;
    private final UserService userService;

    @Setter
    @Getter
    private Long id;

    @Getter
    @Setter
    private OpinionEditModel opinion;

    @Getter
    private List<DishModel> dishes;


    @Inject
    public OpinionCreate(OpinionService opinionService, DishService dishService, UserService userService) {
        this.opinionService = opinionService;
        this.dishService = dishService;
        this.userService = userService;
    }

    public void init() throws IOException {
        this.opinion = new OpinionEditModel();
        this.opinion.setDish(new DishModel());
        this.dishes = dishService.findAll().stream()
                .map(DishModel.entityToModelMapper())
                .collect(Collectors.toList());
    }

    public String saveAction() {
        opinion.setUser(userService.getCurrentUser());
        opinionService.create(OpinionEditModel.modelToEntityMapper().apply(opinion));
        return null;
    }
}
