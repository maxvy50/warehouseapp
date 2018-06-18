package edu.dartit.warehouseapp.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by vysokov-mg on 31.05.2018.
 */
public class WhaSessionFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (isAuthorized(request, response)) {
            chain.doFilter(req, resp);
        }
    }

    private boolean isAuthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* free access to this resources is required for
         * Thymeleaf engine and jQuery on the front end
         */
        String uri = request.getRequestURI();
        if (uri.startsWith("/css") || uri.startsWith("/templates") || uri.startsWith("/scripts")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if ("/auth".equals(uri) || "/register".equals(uri)) {
            if (session != null) {
                response.sendRedirect("/");
                return false;
            }
        } else {
            if (session == null) {
                goAuthorize(request, response);
                return false;
            }
        }

        return true;
    }

    private void goAuthorize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("isAJAX") != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.sendRedirect("/auth");
        }
    }


    public void init(FilterConfig config) throws ServletException {
    }
}
