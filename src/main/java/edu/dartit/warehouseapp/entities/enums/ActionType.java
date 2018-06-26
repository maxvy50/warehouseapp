package edu.dartit.warehouseapp.entities.enums;

/**
 * Created by vysokov-mg on 20.06.2018.
 */
public enum ActionType {
    add_item("Оприходование"), post_item("Этого не должно быть видно"),
    move_item("Перемещение"), sell_item("Продажа");

    private final String s;

    ActionType(String s) {
        this.s = s;
    }

    public String toString() {
        return this.s;
    }
}
