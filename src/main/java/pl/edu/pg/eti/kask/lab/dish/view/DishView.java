package pl.edu.pg.eti.kask.lab.dish.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.model.DishViewModel;
import pl.edu.pg.eti.kask.lab.dish.model.DishesModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
@NoArgsConstructor
public class DishView implements Serializable {
    private DishService dishService;
    private OpinionService opinionService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private DishViewModel dish;

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @EJB
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }
    public void init() throws IOException {
        System.out.println("init");
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            this.dish= DishViewModel.entityToModelMapper().apply(dish.get(),
                    opinionService.findAllForDish(dish.get().getId()));
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Dish not found");
        }
    }

    public String deleteAction(Long opinionId) {
        opinionService.delete(opinionService.find(opinionId).get());
        //opinionService.delete(Opinion.builder().id(opinionId).build());
        return "dish_view?faces-redirect=true&id=" + id;
    }
}
