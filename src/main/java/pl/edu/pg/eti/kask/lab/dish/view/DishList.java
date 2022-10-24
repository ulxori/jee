package pl.edu.pg.eti.kask.lab.dish.view;

import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.model.DishesModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class DishList {
    private final DishService dishService;
    private DishesModel dishes;

    @Inject
    public DishList(DishService dishService) {
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
        dishService.delete(Dish.builder().id(dish.getId()).build());
        return "dish_list?faces-redirect=true";
    }
}
