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


public class WhaListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public WhaListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This is to establish a connection to the database
        * */
        ServletContext servletContext = sce.getServletContext();
        try {
            servletContext.setAttribute("dbManager", new DbManager(servletContext));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        /* This is to configure Thymeleaf engine
        * */
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
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        ServletContext servletContext = sce.getServletContext();
        DbManager dbManager = (DbManager) servletContext.getAttribute("dBManager");

        try {
            dbManager.doDie();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    @Override
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
