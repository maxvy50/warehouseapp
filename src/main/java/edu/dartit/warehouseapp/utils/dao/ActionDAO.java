package edu.dartit.warehouseapp.utils.dao;

import edu.dartit.warehouseapp.entities.*;
import edu.dartit.warehouseapp.entities.enums.ActionType;
import edu.dartit.warehouseapp.utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vysokov-mg on 20.06.2018.
 */
public class ActionDAO {

    public static List<Action> getAll() throws DAOException {

    return null;
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
