package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.dao.OrgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 19.06.2018.
 */

public class AddOrgServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String orgName = request.getParameter("orgName");
        String region = request.getParameter("orgRegion");
        String address = request.getParameter("orgAddress");

        OrgDAO orgDAO = new OrgDAO();

        try {
            orgDAO.add(new Organization(orgName, region, address));
            sendJson(orgDAO.getAll(), response);
        }
        catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrgDAO orgDAO = new OrgDAO();
        try {
            new ThymePage(request, response)
                    .addVariable("orgsList", orgDAO.getAll())
                    .addVariable("username", (String) request.getSession(false).getAttribute("username"))
                    .process("addOrg");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
