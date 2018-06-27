package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.dao.OrgDAO;
import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.utils.ThymePage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vysokov-mg on 19.06.2018.
 */

public class EditOrgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orgName = request.getParameter("orgName");
        String region = request.getParameter("orgRegion");
        String address = request.getParameter("orgAddress");

        Organization org = new Organization(orgName, region, address);

        try {
            OrgDAO.edit(org);
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            new ThymePage(request, response)
                    .addVariable("orgsList", OrgDAO.getAll())
                    .addVariable("username", (String) request.getSession(false).getAttribute("username"))
                    .process("editOrg");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }

    }
}
