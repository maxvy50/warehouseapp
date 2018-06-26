package edu.dartit.warehouseapp.dao;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.entities.enums.ActionType;
import edu.dartit.warehouseapp.utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vysokov-mg on 20.06.2018.
 */
public class ActionDAO {

    public static List<Action> getActionJournalFor(Organization org) throws DAOException {

        String query = "SELECT * FROM actions WHERE supplier='" + org.getName() + "' OR consumer='" + org.getName() + "';";

        List<Action> result = new ArrayList<>();

        try (
                Connection conn = DBConnector.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new Action()
                        .setId(rs.getInt("action_id"))
                        .setDate(rs.getDate("action_date").toLocalDate())
                        .setType(ActionType.valueOf(rs.getString("action_type")))
                        .setSupplier(OrgDAO.getByKey(rs.getString("supplier")))
                        .setConsumer(OrgDAO.getByKey(rs.getString("consumer")))
                        .setItem(ItemDAO.getByKey(rs.getString("item_name")))
                        .setUser(new User(rs.getString("actor")))
                        .setAmount(rs.getInt("amount"))
                );

            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public static void add(Action action) throws DAOException {

        ActionType type = action.getType();

        Organization supplier = action.getSupplier();
        Organization consumer = action.getConsumer();
        Item item = action.getItem();
        int amount = action.getAmount();
        User actor = action.getUser();

        String stmnt = "INSERT INTO actions (action_type, supplier, consumer, item_name, amount, actor) " +
                "VALUES ('" + type.name() +
                "', " + (supplier == null ? null : "'" + supplier.getName() + "'") +
                ", " + (consumer == null ? null : "'" + consumer.getName() + "'") +
                ", '" + item.getName() +
                "', " + amount +
                ", '" + actor.getUsername() +
                "')";

        if (executeUpdate(stmnt) == 0) {
            throw new DAOException("Не удалось зарегистрировать действие");
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
