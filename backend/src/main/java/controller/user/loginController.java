package controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import controller.RequestReader;
import controller.ResponseWriter;
import error.NoConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.dto.User;

@WebServlet("/login")
public class loginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String acceptType = (req.getHeader("Accept") != null)
                ? req.getHeader("Accept")
                : "application/json;charset=UTF-8";
        resp.setContentType(acceptType);

        User credentials = new RequestReader<User>().readAsObject(req, User.class);
        if (credentials == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "login and password required");
            return;
        }

        try {
            Optional<User> userTypeOptional = UserDao.getUserByLoginPassword(
                credentials.getLogin(),
                credentials.getPasswordHash()
            );
            if (userTypeOptional.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_CONFLICT);
            } else {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                new ResponseWriter<User>().writeResponse(resp, acceptType, userTypeOptional.get());
            }
        } catch (SQLException | NoConnection e) {
            System.err.println(e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } 
        return;
    }
}
