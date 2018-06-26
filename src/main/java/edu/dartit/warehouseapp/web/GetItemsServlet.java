package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.dao.OrgDAO;
import edu.dartit.warehouseapp.dao.OrgsHasItemsDAO;
import edu.dartit.warehouseapp.utils.JsonSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 26.06.2018.
 */

public class GetItemsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orgName = request.getParameter("orgName");
        try {
            sendJson(OrgsHasItemsDAO.getItemsFor(OrgDAO.getByKey(orgName)), response);
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
