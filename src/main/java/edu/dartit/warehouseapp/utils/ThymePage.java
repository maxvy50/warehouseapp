package edu.dartit.warehouseapp.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

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
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        req = request;

        resp = response;
        resp.setCharacterEncoding("UTF-8");

        webContext = new WebContext(request, response, servletContext, new Locale("ru"));
    }

    public ThymePage addVariable(String name, Object value) {
        webContext.setVariable(name, value);
        return this;
    }

    public void process(String template) throws IOException {
        templateEngine.process(template, webContext, resp.getWriter());
    }
}
