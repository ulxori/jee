package pl.edu.pg.eti.kask.lab.portrait.servlet;

import pl.edu.pg.eti.kask.lab.portrait.entity.Portrait;
import pl.edu.pg.eti.kask.lab.portrait.manager.PortraitManager;
import pl.edu.pg.eti.kask.lab.portrait.service.PortraitService;
import pl.edu.pg.eti.kask.lab.servlet.MimeTypes;
import pl.edu.pg.eti.kask.lab.servlet.ServletUtility;
import pl.edu.pg.eti.kask.lab.user.entity.User;
import pl.edu.pg.eti.kask.lab.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = PortraitServlet.Paths.PORTRAITS + "/*")
@MultipartConfig(maxFileSize = 20000 * 1024)
public class PortraitServlet extends HttpServlet {

    private UserService userService;
    private PortraitService portraitService;
    private PortraitManager portraitManager;

    @Inject
    public PortraitServlet(UserService userService, PortraitService portraitService, PortraitManager portraitManager) {
        this.userService = userService;
        this.portraitService = portraitService;
        this.portraitManager = portraitManager;
    }

    public static class Paths {
        public static final String PORTRAITS = "/api/portrait";
    }

    public static class Patterns {
        public static final String PORTRAIT = "^/[0-9]+/?$";
    }

    public static class Parameters {
        public static final String PORTRAIT = "portrait";

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();

        if (Paths.PORTRAITS.equals(servletPath)) {
          if (path.matches(Patterns.PORTRAIT)) {
              getPortrait(req, resp);
              System.out.println("ff");
              //System.out.println(Config.properties);
              System.out.println(portraitManager.getPortraits());
              return;
          }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();

        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                postPortrait(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();

        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                putPortrait(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();

        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                deletePortrait(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getPortrait(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = userService.find(id);

        if (user.isPresent()) {
            Optional<Portrait> userPortrait = portraitService.getPortrait(id);

            if (userPortrait.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            resp.setContentLength(userPortrait.get().getPortrait().length);
            resp.getOutputStream().write(userPortrait.get().getPortrait());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postPortrait(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = userService.find(id);

        if (user.isPresent()) {
            if (portraitService.getPortrait(id).isPresent()) {
                resp.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            Part portrait = req.getPart(Parameters.PORTRAIT);
            if (portrait != null) {
                System.out.println("here");
                portraitService.addPortrait(id, portrait.getInputStream());
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void putPortrait(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = userService.find(id);

        if (user.isPresent()) {
            if (portraitService.getPortrait(id).isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Part portrait = req.getPart(Parameters.PORTRAIT);
            portraitService.updatePortrait(id, portrait.getInputStream());
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void deletePortrait(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<User> user = userService.find(id);

        if (user.isPresent()) {
            Optional<Portrait> portrait = portraitService.getPortrait(id);
            if (portrait.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            portraitService.deletePortrait(portrait.get());
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
