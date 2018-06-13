package edu.dartit.warehouseapp.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vysokov-mg on 06.06.2018.
 */
public class DataTable implements Reportable {

    protected ArrayList<ArrayList<String>> data;

    protected DataTable(ArrayList<String> fields, ResultSet rs) throws SQLException {

        int columnsNumber = rs.getMetaData().getColumnCount();
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        data.add(fields);

        while (rs.next()) {
            ArrayList<String> row = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        this.data = data;
    }

    @Override
    public ArrayList<ArrayList<String>> doReport() {
        return data;
    }
}
