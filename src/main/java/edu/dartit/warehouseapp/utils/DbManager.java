package edu.dartit.warehouseapp.utils;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vysokov-mg on 07.06.2018.
 */
public class DbManager {


    private Connection connection;



    public DbManager(ServletContext servletContext) throws SQLException {

        String dbUrl = servletContext.getInitParameter("dbUrl");
        String dbUser = servletContext.getInitParameter("dbUser");
        String dbPassword = servletContext.getInitParameter("dbPassword");

        Jdbc3PoolingDataSource ds = new Jdbc3PoolingDataSource();
        ds.setUrl(dbUrl);
        ds.setUser(dbUser);
        ds.setPassword(dbPassword);

        this.connection = ds.getConnection();

    }



    public ArrayList<ArrayList<String>> selectFrom(String table, String... labels) throws SQLException {

        StringBuilder sb = new StringBuilder();
        for (String label : labels) {
            sb.append(label).append(',');
        }
        String validLabels = (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "*";

        String queryString = "SELECT " + validLabels + " FROM " + table + ";";

        return rsToList(connection.prepareStatement(queryString).executeQuery());
    }




    public ArrayList<ArrayList<String>> executeQuery(String queryString) throws SQLException {

        return rsToList(connection.prepareStatement(queryString).executeQuery());
    }




    public void doDie() throws SQLException {
        this.connection.close();
    }




    private ArrayList<ArrayList<String>> rsToList(ResultSet rs) throws SQLException {

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        int columnsNumber = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            ArrayList<String> row = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        return data;
    }

}
