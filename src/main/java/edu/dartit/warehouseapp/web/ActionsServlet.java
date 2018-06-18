package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.utils.DAOException;
import edu.dartit.warehouseapp.utils.OrgDAO;
import edu.dartit.warehouseapp.utils.ThymePage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vysokov-mg on 07.06.2018.
 */
public class ActionsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrgDAO orgDAO = new OrgDAO();
        try {
            new ThymePage(request, response)
                    .addVariable("orgList", orgDAO.getAll())
                    .process("actions");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }

    }
}
