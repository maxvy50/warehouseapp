package edu.dartit.warehouseapp.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vysokov-mg on 05.06.2018.
 */

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = request.getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");

        WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
        webContext.setVariable("pageName", "Registration");
        webContext.setVariable("formToLoad", "registrationForm");

        templateEngine.process("auth", webContext, response.getWriter());
    }
}
