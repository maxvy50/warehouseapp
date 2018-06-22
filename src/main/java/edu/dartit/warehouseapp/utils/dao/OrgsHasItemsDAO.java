package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.entities.Record;
import edu.dartit.warehouseapp.entities.enums.ItemType;
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

/*    public static OrgsHasItems getByKey(Organization org, Item item) throws DAOException {

        if (org == null || item == null) {
            return null;
        }

        String query = "SELECT amount FROM orgs_has_items WHERE org_name='" + org.getName() +
                "' AND item_name='" + item.getName() + "';";

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return new OrgsHasItems(org, item, Integer.parseInt(rs.getString("amount")));
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }


    public static List<OrgsHasItems> getAll() throws DAOException {

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
    }*/


    public static void add(Organization org, Item item, int amount) throws DAOException {

        if (!ItemDAO.has(item)) {
            ItemDAO.add(item);
        }

        String stmnt = "INSERT INTO orgs_has_items (org_name, item_name, amount) " +
                "VALUES ('" + org.getName() + "', '" + item.getName() + "', " + amount + ") " +
                "ON CONFLICT (org_name, item_name) DO UPDATE SET amount = orgs_has_items.amount + " + amount + ";";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Проблемы при добавлении ТМЦ на склад");
        }
    }


    public static void pickUp(Organization org, Item item, int amount) throws DAOException {

        String stmnt = "UPDATE orgs_has_items SET amount = orgs_has_items.amount - " + amount +
                "WHERE org_name='" + org.getName() + "' AND item_name='" + item.getName() + "';";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Проблемы при отгрузке ТМЦ со склада");
        }
    }


    public static List<Record> getItemsFor(Organization org) throws DAOException {

        String query = "select items.item_name, items.item_type, orgs_has_items.amount from " +
                "items INNER JOIN orgs_has_items on items.item_name = orgs_has_items.item_name where org_name='" + org.getName() + "';";

        List<Record> result = new ArrayList<>();

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                result.add(
                        new Record()
                                .addData(rs.getString("item_name"))
                                .addData(ItemType.valueOf(rs.getString("item_type")))
                                .addData(rs.getInt("amount"))
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return result;
    }


    private static int executeUpdate(String stmnt) throws DAOException {

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
