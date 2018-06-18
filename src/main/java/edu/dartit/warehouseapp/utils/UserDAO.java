package edu.dartit.warehouseapp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;

/**
 * Created by vysokov-mg on 15.06.2018.
 */
public class UserDAO {

    public boolean add(String username, String password) throws DAOException {

        String stmnt = "INSERT INTO users (username, password, salt) " +
                "VALUES ('" + username + "', '" + shaHex(password) + "', '" + "" +"');";
                                                                 //FIXME SALT ^
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

    public boolean has(String username, String password) throws DAOException {

        String query = "SELECT username FROM users WHERE username='" + username +
                "' AND password='" + shaHex(password) + "';";
        return executeQuery(query);

    }

    public boolean has(String username) throws DAOException {

        String query = "SELECT username FROM users WHERE username='" + username + "';";
        return executeQuery(query);
    }

    private boolean executeQuery(String query) throws DAOException {

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

}
