package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public class OrgsHasItemsDAO {

    public List<OrgsHasItems> getAll() throws DAOException {

        String query = "SELECT * FROM orgs_has_items";

        List<OrgsHasItems> result = new ArrayList<>();

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            OrgDAO orgDAO = new OrgDAO();
            ItemDAO itemDAO = new ItemDAO();
            while (rs.next()) {
                Organization org = orgDAO.getByKey(rs.getString("org_name"));
                Item item = itemDAO.getByKey(rs.getString("item_name"));
                result.add(new OrgsHasItems(org, item, Integer.parseInt(rs.getString("amount")))
                );
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean add(OrgsHasItems ohi, User sign) throws DAOException {

        Item item = ohi.getItem();
        String orgName = ohi.getOrg().getName();
        String itemName = item.getName();
        int amount = ohi.getAmount();

        ItemDAO itemDAO = new ItemDAO();
        if (!itemDAO.has(item)) {
            itemDAO.add(item);
        }

        String stmnt = "INSERT INTO orgs_has_items (org_name, item_name, amount) " +
                "VALUES ('" + orgName + "', '" + itemName + "', " + amount + ") " +
                "ON CONFLICT (org_name, item_name) DO UPDATE SET amount = orgs_has_items.amount + " + amount + ";";
        boolean result = executeUpdate(stmnt) != 0;

        Action action = new Action()
                .setType(ActionType.add_item)
                .setUser(sign)
                .setConsumer(ohi.getOrg())
                .setItem(item)
                .setAmount(amount);

        ActionDAO actionDAO = new ActionDAO();
        actionDAO.add(action);

        return result;
    }

    private int executeUpdate(String stmnt) throws DAOException {

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(stmnt)
        ) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}