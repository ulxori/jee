package pl.edu.pg.eti.kask.lab.opinion.controller;

import pl.edu.pg.eti.kask.lab.dish.dtos.CreateDishOpinionRequest;
import pl.edu.pg.eti.kask.lab.dish.entity.Dish;
import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.dtos.GetDishOpinionResponse;
import pl.edu.pg.eti.kask.lab.opinion.dtos.GetDishOpinionsResponse;
import pl.edu.pg.eti.kask.lab.opinion.dtos.UpdateDishOpinionRequest;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;
import pl.edu.pg.eti.kask.lab.user.entity.UserRoles;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Path("/dishes/{dishId}/opinions")
@RolesAllowed(UserRoles.USER)
public class DishOpinionController {
    private OpinionService opinionService;
    private UserService userService;
    private DishService dishService;

    public DishOpinionController() {
    }

    @EJB
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @EJB
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishOpinions(@PathParam("dishId") Long dishId) {
        List<Opinion> opinions = opinionService.findAllForDish(dishId);
        return Response
                .ok(GetDishOpinionsResponse.entityToDtoMapper().apply(opinions))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishOpinion(@PathParam("dishId") Long dishId, @PathParam("id") Long id) {
        Optional<Dish> dish = dishService.find(dishId);
        Optional<Opinion> opinion = opinionService.find(id);
        if (opinion.isEmpty() || dish.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response
                .ok(GetDishOpinionResponse.entityToDtoMapper().apply(opinion.get()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDishOpinion(@PathParam("dishId") Long id, CreateDishOpinionRequest request) {
        Opinion opinion = CreateDishOpinionRequest
                .dtoToEntityMapper(userId -> userService.find(userId).orElse(null), () -> dishService.find(id).orElse(null))
                .apply(request);
        if (opinion.getDish() == null || opinion.getUser() == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        opinionService.create(opinion);
        System.out.println(opinion);
        return Response
                .created(UriBuilder
                        .fromMethod(DishOpinionController.class, "getDishOpinion")
                        .build(opinion.getId()))
                .build();
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putDishOpinion(@PathParam("dishId") Long dishId, @PathParam("id") Long id, UpdateDishOpinionRequest request) {
        Opinion updatedOpinion = UpdateDishOpinionRequest
                .dtoToEntityMapper(userId -> userService.find(userId).orElse(null), () -> dishService.find(dishId).orElse(null))
                .apply(request);

       Optional<Opinion> old = opinionService.find(id);

        if (old.isEmpty() || updatedOpinion.getDish() == null || updatedOpinion.getUser() == null) {
            System.out.println("not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        updatedOpinion.setId(old.get().getId());
        opinionService.update(updatedOpinion);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
    @DELETE
    @Path("{id}")
    public Response deleteDishOpinion(@PathParam("id") Long id) {
        Optional<Opinion> opinion = opinionService.find(id);
        if (opinion.isEmpty()) {
           return Response
                   .status(Response.Status.NOT_FOUND)
                   .build();
        }
        opinionService.delete(opinion.get());
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}
