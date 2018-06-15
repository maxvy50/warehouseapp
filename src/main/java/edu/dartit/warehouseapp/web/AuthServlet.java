package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.utils.ThymePage;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
/*        String authString = "SELECT username FROM users WHERE username='" + username +
                "' AND password='" + shaHex(password) + "';";*/
        try {
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            if (userDAO.contains(username) && userDAO.isPasswordValid(password)) {
                    String uuid = UUID.fromString(username).toString();
                    request.getSession().setAttribute("UUID", uuid);
                    Cookie cookie = new Cookie("UUID", uuid);
                    response.addCookie(cookie);
                    response.setStatus(HttpServletResponse.SC_OK);
            }
        }
        catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ThymePage(request, response).addVariable("pageName", "Authorization")
                                        .addVariable("formToLoad", "authForm")
                                        .process("auth");
    }

}
