package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.User;
import edu.dartit.warehouseapp.utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by vysokov-mg on 15.06.2018.
 */
public class UserDAO {

    public static boolean add(User user) throws DAOException {

        String username = user.getUsername();
        String password = user.getPassword();

        String stmnt = "INSERT INTO users (username, password, salt) " +
                "VALUES ('" + username + "', '" + password + "', '" + "" + "');";
                                                         //FIXME SALT ^
        return executeUpdate(stmnt) != 0;
    }

    public static boolean isValid(User user) throws DAOException {

        String query = "SELECT username FROM users WHERE username='" + user.getUsername() +
                "' AND password='" + user.getPassword() + "';";

        return executeQuery(query);

    }

    public static boolean has(User user) throws DAOException {
        return getByKey(user.getUsername()) != null;
    }

    public static User getByKey(String username) throws DAOException {

        String query = "SELECT * FROM users WHERE username='" + username + "';";

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private static boolean executeQuery(String query) throws DAOException {

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
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
