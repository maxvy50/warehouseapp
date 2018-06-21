package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.dao.OrgDAO;
import edu.dartit.warehouseapp.utils.dao.OrgsHasItemsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 19.06.2018.
 */

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String itemName = request.getParameter("itemName");
        ItemType type = ItemType.valueOf(request.getParameter("itemType"));
        int amount = Integer.parseInt(request.getParameter("itemAmount"));
        String orgName = request.getParameter("orgName");
        Item item = new Item(itemName, type);
        User user = new User(
                (String)request.getSession(false).getAttribute("username")
        );

        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();
        OrgDAO orgDAO = new OrgDAO();
        try {
            Organization org = orgDAO.getByKey(orgName);
            OrgsHasItems record = new OrgsHasItems(org, item, amount);
            ohiDAO.add(record, user);
            sendJson(ohiDAO.getAll(), response);
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrgDAO orgDAO = new OrgDAO();
        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();
        try {
            new ThymePage(request, response)
                    .addVariable("itemTypes", ItemType.values())
                    .addVariable("orgsList", orgDAO.getAll())
                    .addVariable("ohiList", ohiDAO.getAll())
                    .addVariable("username", (String) request.getSession(false).getAttribute("username"))
                    .process("addItem");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
