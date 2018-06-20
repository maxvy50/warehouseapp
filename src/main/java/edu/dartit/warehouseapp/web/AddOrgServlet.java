package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.dao.OrgDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                    .addVariable("pageName", "Добавление организации")
                    .addVariable("formToLoad", "addOrgForm")
                    .addVariable("tableToLoad", "orgsTable")
                    .addVariable("orgList", orgDAO.getAll())
                    .process("action");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
