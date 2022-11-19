package pl.edu.pg.eti.kask.lab.dish.view;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.model.DishesModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
@NoArgsConstructor
public class DishList {
    private DishService dishService;
    private DishesModel dishes;

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public DishesModel getDishes() {
        if (dishes == null) {
            dishes = DishesModel.entityToDtoMapper().apply(dishService.findAll());
        }
        return dishes;
    }

    public String deleteAction(DishesModel.Dish dish) {
        System.out.println("inside");
        dishService.delete(dishService.find(dish.getId()).get());
        //dishService.delete(Dish.builder().id(dish.getId()).build());
        return "dish_list?faces-redirect=true";
    }
}
