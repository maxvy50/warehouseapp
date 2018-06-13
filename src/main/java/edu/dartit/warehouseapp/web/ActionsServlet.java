package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.utils.DbManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vysokov-mg on 07.06.2018.
 */
public class ActionsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = request.getServletContext();
        DbManager dbManager = (DbManager)servletContext.getAttribute("dbManager");
        TemplateEngine templateEngine = (TemplateEngine)servletContext.getAttribute("templateEngine");

        WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
        webContext.setVariable("pageName", "Actions");
        try {
            webContext.setVariable("orgList", dbManager.selectFrom("organizations", "org_name"));
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        templateEngine.process("actions", webContext, response.getWriter());
    }
}
