package edu.dartit.warehouseapp.web; /**
 * Created by vysokov-mg on 31.05.2018.
 */

import edu.dartit.warehouseapp.utils.DbManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;


public class WhaListener implements ServletContextListener {

    public WhaListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This is to configure Thymeleaf engine
        * */
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

        ServletContext servletContext = sce.getServletContext();
        DbManager dbManager = (DbManager) servletContext.getAttribute("dBManager");

        try {
            dbManager.doDie();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
