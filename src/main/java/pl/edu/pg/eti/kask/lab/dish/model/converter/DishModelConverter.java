package pl.edu.pg.eti.kask.lab.dish.model.converter;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.model.DishModel;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = DishModel.class, managed = true)
@NoArgsConstructor
public class DishModelConverter implements Converter<DishModel> {

    private DishService service;

    @EJB
    public void setDishService(DishService service) {
        this.service = service;
    }

    @Override
    public DishModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Dish> dish = service.find(Long.parseLong(value.split(" ")[0]));
        return dish.isEmpty() ? null : DishModel.entityToModelMapper().apply(dish.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, DishModel value) {
        return value == null ? "" : value.getId() + " " + value.getName();
    }
}

