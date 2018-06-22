package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.entities.enums.ActionType;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.utils.dao.*;

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

        try {
            Organization org = OrgDAO.getByKey(orgName);
            Item item = ItemDAO.getByKey(itemName);
            Action action = new Action()
                    .setType(ActionType.sell_item)
                    .setUser(user)
                    .setSupplier(org)
                    .setItem(item)
                    .setAmount(amount);

            org.sell(item, amount);
            ActionDAO.add(action);
            sendJson(org.getItemJournal(), response);
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            new ThymePage(request, response)
                    .addVariable("username", (String) request.getSession().getAttribute("username"))
                    .addVariable("orgsList", OrgDAO.getAll())
                    .process("sellItem");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
