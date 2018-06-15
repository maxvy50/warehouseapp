package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.utils.DbManager;
import edu.dartit.warehouseapp.utils.ThymePage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by vysokov-mg on 05.06.2018.
 */

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRep = request.getParameter("passwordRep");

        ServletContext servletContext = request.getServletContext();
        DbManager dbManager = (DbManager)servletContext.getAttribute("dbManager");
        String authString = "SELECT username FROM users WHERE username='" + username + "';";
        try {
            if (dbManager.executeQuery(authString).size() > 0) {
                response.getWriter().print("Specified username already in use!");
            } else {
                if (password.equals(passwordRep)) {

                }

            }
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ThymePage(request, response).addVariable("pageName", "Registration")
                                        .addVariable("formToLoad", "registrationForm")
                                        .process("auth");
    }
}
