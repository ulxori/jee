package pl.edu.pg.eti.kask.lab.opinion.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.model.OpinionEditModel;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
@NoArgsConstructor
public class OpinionCreate implements Serializable {
    private OpinionService opinionService;
    private DishService dishService;
    private UserService userService;

    @Setter
    @Getter
    private Long id;

    @Getter
    @Setter
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

    @EJB
    public void setUserService(UserService userService) {
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
