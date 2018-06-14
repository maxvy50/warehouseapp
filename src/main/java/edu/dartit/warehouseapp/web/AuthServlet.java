package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.utils.DbManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;


public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = request.getServletContext();
        DbManager dbManager = (DbManager) servletContext.getAttribute("dbManager");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String authString = "SELECT username FROM users WHERE username='" + username +
                "' AND password='" + shaHex(password) + "';";
        try {
            if (dbManager.executeQuery(authString).size() == 0) {
                response.setContentType("text/html");
                response.getWriter().print("Wrong username or password!");
            } else {
                String uuid = UUID.randomUUID().toString();
                request.getSession().setAttribute("UUID", uuid);
                Cookie cookie = new Cookie("UUID", uuid);
                response.addCookie(cookie);
            }
        }
        catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = request.getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");

        WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
        webContext.setVariable("pageName", "Authorization");
        webContext.setVariable("formToLoad", "authForm");

        templateEngine.process("auth", webContext, response.getWriter());
    }

}
