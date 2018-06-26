package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.User;
import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;

/**
 * Created by vysokov-mg on 05.06.2018.
 */

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRep = request.getParameter("passwordRep");

        UserDAO userDAO = new UserDAO();
        User user = new User(username, shaHex(password));

        try {
            if (userDAO.has(user)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                if (password.equals(passwordRep)) {
                    userDAO.add(user);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ThymePage(request, response)
                .addVariable("pageName", "Регистрация")
                .addVariable("formToLoad", "registrationForm")
                .process("auth");
    }
}
