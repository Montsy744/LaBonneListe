package controller.user;

import java.sql.SQLException;
import java.util.Optional;

import MiddleWare.IdMiddleWare;
import controller.ResponseWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.dto.User;

public class userController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        
        String acceptType = (request.getHeader("Accept") != null)
                ? request.getHeader("Accept")
                : "application/json;charset=UTF-8";
        response.setContentType(acceptType);
        Optional<String> optionalId = IdMiddleWare.getPath(request, 2);
        if(optionalId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "request must have an id");
        }
        try {
            int id = Integer.parseInt(optionalId.get());
            Optional<User> userOptional = UserDao.getUserById(id);
            if(userOptional.isPresent()) {
                new ResponseWriter<User>().writeResponse(response, acceptType, userOptional.get());
            }  else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "id must be a number" + e);
        }

    }
}
