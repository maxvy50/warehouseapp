package edu.dartit.warehouseapp.utils;

import edu.dartit.warehouseapp.entities.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vysokov-mg on 18.06.2018.
 */
public class OrgDAO {

    public List<Organization> getAll() throws DAOException {

        String query = "SELECT * FROM organizations";

        List<Organization> result = new ArrayList<>();

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new Organization(
                        rs.getString("org_name"),
                        rs.getString("region"),
                        rs.getString("address")));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean add(Organization org) throws DAOException {

        String stmnt = "INSERT INTO organizations (org_name, address, region) " +
                "VALUES ('" + org.getName() + "', '" + org.getRegion() + "', '" + org.getAddress() + "')";
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
