package edu.dartit.warehouseapp.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vysokov-mg on 15.06.2018.
 */
public class ThymePage {

    private WebContext webContext;
    private TemplateEngine templateEngine;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public ThymePage(HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = request.getServletContext();
        req = request;
        resp = response;
        templateEngine = (TemplateEngine)servletContext.getAttribute("templateEngine");
        webContext = new WebContext(request, response, servletContext, request.getLocale());
    }

    public ThymePage addVariable(String name, Object value) {
        webContext.setVariable(name, value);
        return this;
    }

    public void process(String template) throws IOException {
        templateEngine.process(template, webContext, resp.getWriter());
    }
}
