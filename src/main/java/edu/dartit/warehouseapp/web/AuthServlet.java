package edu.dartit.warehouseapp.web;

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


public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        try {
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            if (userDAO.has(username, password)) {
                String uuid = UUID.randomUUID().toString();
                request.getSession().setAttribute("UUID", uuid);
                response.addCookie(new Cookie("UUID", uuid));
                response.addCookie(new Cookie("User", username));
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
