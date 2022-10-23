package pl.edu.pg.eti.kask.lab.opinion.controller;

import pl.edu.pg.eti.kask.lab.dish.service.DishService;
import pl.edu.pg.eti.kask.lab.opinion.dtos.GetDishOpinionResponse;
import pl.edu.pg.eti.kask.lab.opinion.dtos.GetDishOpinionsResponse;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;
import pl.edu.pg.eti.kask.lab.opinion.service.OpinionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/dishes/{dishId}/opinions")
public class DishOpinionController {
    private OpinionService opinionService;

    public DishOpinionController() {
    }

    @Inject
    public void setDishService(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @Inject
    public void setOpinionService(OpinionService opinionService) {
        this.opinionService = opinionService;
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
    public Response getDishOpinion(@PathParam("id") Long id) {
        Optional<Opinion> opinion = opinionService.find(id);
        if (opinion.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response
                .ok(GetDishOpinionResponse.entityToDtoMapper().apply(opinion.get()))
                .build();
    }
    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDishOpinion() {

    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putDishOpinion(@PathParam("id") Long id) {

    }

    @PUT
    @Path("{id}")
    public Response deleteDishOpinion(@PathParam("id") Long id) {

    }*/
}
