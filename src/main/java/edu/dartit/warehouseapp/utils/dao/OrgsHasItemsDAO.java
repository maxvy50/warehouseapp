package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.Organization;
import edu.dartit.warehouseapp.entities.OrgsHasItems;
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
            while (rs.next()) {
                result.add(new OrgsHasItems(
                        rs.getString("org_name"),
                        rs.getString("item_name"),
                        Integer.parseInt(rs.getString("amount")))
                );
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean add(OrgsHasItems ohi) throws DAOException {

        String stmnt = "INSERT INTO orgs_has_items (org_name, item_name, amount) " +
                "VALUES ('" + ohi.getOrgName() + "', '" + ohi.getItemName() + "', '" + ohi.getAmount() + "')";
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
