package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.ItemTypes;
import edu.dartit.warehouseapp.entities.OrgsHasItems;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.dao.ItemDAO;
import edu.dartit.warehouseapp.utils.dao.OrgDAO;
import edu.dartit.warehouseapp.utils.dao.OrgsHasItemsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vysokov-mg on 19.06.2018.
 */

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String itemName = request.getParameter("itemName");
        ItemTypes type = ItemTypes.valueOf(request.getParameter("itemType"));
        int amount = Integer.parseInt(request.getParameter("itemAmount"));
        String orgName = request.getParameter("orgName");

        ItemDAO itemDAO = new ItemDAO();
        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();
        try {
            if (itemDAO.getByPK(itemName) == null) {
                itemDAO.add(new Item(itemName, type));
            }
            ohiDAO.add(new OrgsHasItems(orgName, itemName, amount));
        }
        catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrgDAO orgDAO = new OrgDAO();
        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();
        try {
            new ThymePage(request, response)
                    .addVariable("itemTypes", ItemTypes.values())
                    .addVariable("orgsList", orgDAO.getAll())
                    .addVariable("ohiList", ohiDAO.getAll())
                    .process("addItem");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
