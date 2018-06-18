package edu.dartit.warehouseapp.utils;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vysokov-mg on 15.06.2018.
 */
public class DBConnector {

    private static final String url = "jdbc:postgresql://localhost:5432/WAREHOUSE_DB";
    private static final String usr = "postgres";
    private static final String pwd = "root";

    private Jdbc3PoolingDataSource ds = new Jdbc3PoolingDataSource();

    private DBConnector() {
        ds.setUrl(url);
        ds.setUser(usr);
        ds.setPassword(pwd);
        ds.setMaxConnections(10);
    }

    private static class Holder {
        static final DBConnector instance = new DBConnector();
    }

    public static DBConnector getInstance() {
        return Holder.instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void closeConnections() {
        ds.close();
    }
}
