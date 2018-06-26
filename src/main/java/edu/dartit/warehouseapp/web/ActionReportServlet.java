package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.dao.ActionDAO;
import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.dao.OrgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 25.06.2018.
 */
public class ActionReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String orgName = request.getParameter("orgName");

        try {
            Organization org = OrgDAO.getByKey(orgName);
            if (org != null) {
                sendJson(ActionDAO.getActionJournalFor(org), response);
            } else {
                throw new DAOException("Указанная организация не зарегистрирована");
            }
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            new ThymePage(request, response)
                    .addVariable("username", (String) request.getSession().getAttribute("username"))
                    .addVariable("orgsList", OrgDAO.getAll())
                    .process("actionReport");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
