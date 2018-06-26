package edu.dartit.warehouseapp.web;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.entities.enums.ActionType;
import edu.dartit.warehouseapp.utils.ThymePage;
import edu.dartit.warehouseapp.dao.ActionDAO;
import edu.dartit.warehouseapp.dao.DAOException;
import edu.dartit.warehouseapp.dao.ItemDAO;
import edu.dartit.warehouseapp.dao.OrgDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static edu.dartit.warehouseapp.utils.JsonSender.sendJson;

/**
 * Created by vysokov-mg on 25.06.2018.
 */
public class ReplaceItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String supplierName = request.getParameter("supplierName");
        String consumerName = request.getParameter("consumerName");
        String itemName = request.getParameter("itemName");
        int amount = Integer.parseInt(request.getParameter("itemAmount"));
        User user = new User(
                (String) request.getSession().getAttribute("username")
        );

        try {
            Organization supplier = OrgDAO.getByKey(supplierName);
            Organization consumer = OrgDAO.getByKey(consumerName);
            Item item = ItemDAO.getByKey(itemName);
            if (item != null) {
                Action action = new Action()
                        .setType(ActionType.move_item)
                        .setUser(user)
                        .setSupplier(supplier)
                        .setConsumer(consumer)
                        .setItem(item)
                        .setAmount(amount);

                supplier.replace(consumer, item, amount);
                ActionDAO.add(action);
                sendJson(supplier.getItemJournal(), consumer.getItemJournal(), response);
            } else {
                throw new DAOException("Указанного наименования нет в базе данных ни одного склада");
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
                    .process("replaceItem");
        } catch (DAOException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
