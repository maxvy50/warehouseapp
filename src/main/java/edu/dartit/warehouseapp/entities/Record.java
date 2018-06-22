package edu.dartit.warehouseapp.entities;

import java.util.ArrayList;

/**
 * Created by vysokov-mg on 22.06.2018.
 */
public class Record {

    private ArrayList<String> content = new ArrayList<>();

    public Record addData(Object obj) {
        content.add(obj.toString());
        return this;
    }
}
