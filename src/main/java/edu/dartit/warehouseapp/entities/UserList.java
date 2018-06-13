package edu.dartit.warehouseapp.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vysokov-mg on 06.06.2018.
 */
public class UserList extends DataTable {

    private static ArrayList<String> fields;

    {
        fields = new ArrayList<>();
        fields.add("Username");
    }

    public UserList(ResultSet rs) throws SQLException {
        super(fields, rs);
    }
}
