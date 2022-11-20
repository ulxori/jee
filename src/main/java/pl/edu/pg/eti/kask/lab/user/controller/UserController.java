package pl.edu.pg.eti.kask.lab.user.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.lab.user.dto.CreateUserRequest;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/users")
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(CreateUserRequest request) {
        User user = CreateUserRequest.dtoToEntityMapper().apply(request);
        userService.create(user);
        return Response.created(UriBuilder.fromPath("/users/{id}").build(user.getId())).build();
    }

}
