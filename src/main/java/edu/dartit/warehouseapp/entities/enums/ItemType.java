package edu.dartit.warehouseapp.entities.enums;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public enum ItemType {

    materials("Материалы"), parts("ЗИП"),
    semifinished("Полуфабрикаты"), goods("Товары"),
    fuels("ГСМ"), waste("Отходы"),
    tare("Тара"), equipment("Снаряжение");

    private final String s;

    private ItemType(String s) {
        this.s = s;
    }

    public String toString() {
        return this.s;
    }


}
