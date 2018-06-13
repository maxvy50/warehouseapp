package edu.dartit.warehouseapp.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vysokov-mg on 06.06.2018.
 */
public class OrganizationList extends DataTable {

    private static ArrayList<String> fields;

    {
        fields = new ArrayList<>();
        fields.add("Name");
        fields.add("Region");
        fields.add("Address");
    }

    public OrganizationList(ResultSet rs) throws SQLException {
        super(fields, rs);
    }
}
