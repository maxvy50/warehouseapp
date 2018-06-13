package edu.dartit.warehouseapp.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vysokov-mg on 07.06.2018.
 */
public class OrgList extends DataTable {

    private static ArrayList<String> fields;

    {
        fields = new ArrayList<>();
        fields.add("Name");
    }

    public OrgList(ResultSet rs) throws SQLException {
        super(fields, rs);
    }
}
