package edu.dartit.warehouseapp.entities;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public enum ItemTypes {

    materials("Материалы"), parts("ЗиП"),
    semifinished("Полуфабрикаты"), goods("Товары"),
    fuels("ГСМ"), waste("Отходы"),
    tare("Тара"), equipment("Снаряжение");

    private final String s;

    private ItemTypes(String s) {
        this.s = s;
    }

    public String toString() {
        return this.s;
    }


}
