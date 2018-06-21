package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.entities.OrgsHasItems;
import edu.dartit.warehouseapp.entities.User;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.dao.ItemDAO;
import edu.dartit.warehouseapp.utils.dao.OrgDAO;
import edu.dartit.warehouseapp.utils.dao.OrgsHasItemsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 21.06.2018.
 */
public class SellItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String orgName = request.getParameter("orgName");
        String itemName = request.getParameter("itemName");
        int amount = Integer.parseInt(request.getParameter("itemAmount"));
        User user = new User(
                (String) request.getSession().getAttribute("username")
        );

        OrgDAO orgDAO = new OrgDAO();
        ItemDAO itemDAO = new ItemDAO();
        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();

        try {
            Organization org = orgDAO.getByKey(orgName);
            Item item = itemDAO.getByKey(itemName);
            OrgsHasItems ohi = new OrgsHasItems(org, item, amount);
            ohiDAO.sell(ohi, user);
            sendJson(ohiDAO.getAll(), response);
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrgDAO orgDAO = new OrgDAO();
        OrgsHasItemsDAO ohiDAO = new OrgsHasItemsDAO();
        try {
            new ThymePage(request, response)
                    .addVariable("username", (String) request.getSession().getAttribute("username"))
                    .addVariable("orgsList", orgDAO.getAll())
                    .addVariable("ohiList", ohiDAO.getAll())
                    .process("sellItem");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
