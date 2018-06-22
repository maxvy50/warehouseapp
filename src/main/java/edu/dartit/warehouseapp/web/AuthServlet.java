package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.User;
import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;


public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User(
                request.getParameter("username"),
                shaHex(request.getParameter("password"))
        );

        try {
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            if (UserDAO.isValid(user)) {

                String uuid = UUID.randomUUID().toString();

                request.getSession().setMaxInactiveInterval(30 * 60);
                request.getSession().setAttribute("UUID", uuid);
                request.getSession().setAttribute("username", user.getUsername());

                response.addCookie(new Cookie("UUID", uuid));
                response.addCookie(new Cookie("username", user.getUsername()));

                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ThymePage(request, response)
                .addVariable("pageName", "Авторизация")
                .addVariable("formToLoad", "authForm")
                .process("auth");
    }

}
