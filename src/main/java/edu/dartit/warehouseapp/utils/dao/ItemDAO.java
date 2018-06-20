package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.ItemTypes;
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
public class ItemDAO {

    public List<Item> getAll() throws DAOException {

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
                        ItemTypes.valueOf(rs.getString("item_type"))));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }


    public Item getByPK(String key) throws DAOException{

        String query = "SELECT * FROM items WHERE item_name='" + key + "';";

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return new Item(
                        rs.getString("item_name"),
                        ItemTypes.valueOf(rs.getString("item_type")));
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean add(Item item) throws DAOException {

        String stmnt = "INSERT INTO items (item_name, item_type) " +
                "VALUES ('" + item.getName() + "', '" + item.getType().name() + "')";
        return executeUpdate(stmnt) != 0;
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
