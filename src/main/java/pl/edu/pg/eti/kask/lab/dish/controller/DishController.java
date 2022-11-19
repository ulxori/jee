package pl.edu.pg.eti.kask.lab.dish.controller;

import pl.edu.pg.eti.kask.lab.dish.dtos.CreateDishRequest;
import pl.edu.pg.eti.kask.lab.dish.dtos.GetDishResponse;
import pl.edu.pg.eti.kask.lab.dish.dtos.GetDishesResponse;
import pl.edu.pg.eti.kask.lab.dish.dtos.UpdateDishRequest;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("/dishes")
public class DishController {
    private DishService dishService;

    public DishController() {
    }

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishes() {
        return Response
                .ok(GetDishesResponse.entityToDtoMapper().apply(dishService.findAll()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDish(@PathParam("id") Long id) {
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            return Response
                    .ok(GetDishResponse.entityToDtoMapper().apply(dish.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDish(@PathParam("id") Long id) {
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            dishService.delete(dish.get());
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDish(CreateDishRequest request) {
        Dish dish = CreateDishRequest
                .dtoToEntityMapper().apply(request);
        dishService.create(dish);
        return Response
                .created(UriBuilder
                        .fromMethod(DishController.class, "getDish")
                        .build(dish.getId()))
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDish(@PathParam("id") Long id, UpdateDishRequest request) {
        Optional<Dish> dish = dishService.find(id);
        if (dish.isPresent()) {
            Dish updated = UpdateDishRequest.dtoToEntityMapper().apply(dish.get(), request);
            dishService.update(updated);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}

