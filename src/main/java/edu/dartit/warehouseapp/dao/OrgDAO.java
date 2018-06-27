package edu.dartit.warehouseapp.dao;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.utils.DBConnector;

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

    public static List<Organization> getAll() throws DAOException {

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

    public static Organization getByKey(String orgName) throws DAOException {

        String query = "SELECT * FROM organizations WHERE org_name='" + orgName + "';";

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return new Organization(
                        rs.getString("org_name"),
                        rs.getString("region"),
                        rs.getString("address")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public static void add(Organization org) throws DAOException {

        String stmnt = "INSERT INTO organizations (org_name, address, region) " +
                "VALUES ('" + org.getName() + "', '" + org.getAddress() + "', '" + org.getRegion() + "')";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Проблемы при добавлении организации");
        }
    }

    public static void edit(Organization org) throws DAOException {
        String stmnt = "UPDATE organizations " +
                "SET address='" + org.getAddress() +
                "', region='" + org.getRegion() +
                "' WHERE org_name='" + org.getName() + "';";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Что-то о5 пошло не так в OrgDAO");
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
