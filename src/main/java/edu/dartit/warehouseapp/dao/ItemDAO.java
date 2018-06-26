package edu.dartit.warehouseapp.dao;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.entities.enums.ItemType;
import edu.dartit.warehouseapp.utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public class ItemDAO {

/*    public static List<Item> getAll() throws DAOException {

        String query = "SELECT * FROM items";

        List<Item> result = new ArrayList<>();

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new Item(
                        rs.getString("item_name"),
                        ItemType.valueOf(rs.getString("item_type"))));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }*/

    public static Item getByKey(String itemName) throws DAOException {

        String query = "SELECT * FROM items WHERE item_name='" + itemName + "';";

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return new Item(
                        rs.getString("item_name"),
                        ItemType.valueOf(rs.getString("item_type"))
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public static boolean has(Item item) throws DAOException {
        return getByKey(item.getName()) != null;
    }

    public static void add(Item item) throws DAOException {

        String stmnt = "INSERT INTO items (item_name, item_type) " +
                "VALUES ('" + item.getName() + "', '" + item.getType().name() + "')";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Проблемы при добавлении ТМЦ в общий реестр");
        }
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
