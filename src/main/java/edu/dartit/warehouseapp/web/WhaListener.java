package edu.dartit.warehouseapp.web; /**
 * Created by vysokov-mg on 31.05.2018.
 */

import edu.dartit.warehouseapp.utils.DBConnector;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class WhaListener implements ServletContextListener {

    public WhaListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        servletContext.setAttribute("templateEngine", templateEngine);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnector.getInstance().closeConnections();
    }

}
