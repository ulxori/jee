package pl.edu.pg.eti.kask.lab.user.servlet;

import pl.edu.pg.eti.kask.lab.servlet.MimeTypes;
import pl.edu.pg.eti.kask.lab.servlet.ServletUtility;
import pl.edu.pg.eti.kask.lab.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.lab.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = UserServlet.Paths.USER + "/*")
public class UserServlet extends HttpServlet {

    private final Jsonb jsonb = JsonbBuilder.create();
    private final UserService userService;

    @Inject
    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    public static class Paths {
        public static final String USER = "/user";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[0-9]+/?$";

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USER.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            GetUserResponse r = GetUserResponse.entityToDtoMapper().apply(user.get());
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAll())));
    }
}
