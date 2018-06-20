package edu.dartit.warehouseapp.entities;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public class Item {

    private String name;
    private ItemTypes type;

    public Item(String name, ItemTypes type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
