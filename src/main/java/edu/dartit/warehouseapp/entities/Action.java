package edu.dartit.warehouseapp.entities;

import edu.dartit.warehouseapp.entities.enums.ActionType;

import java.util.Date;

/**
 * Created by vysokov-mg on 20.06.2018.
 */
public class Action {

    private int id;
    private Date date;
    private ActionType type;
    private Organization supplier;
    private Organization consumer;
    private Item item;
    private int amount;
    private User user;

    public Organization getSupplier() {
        return supplier;
    }

    public Action setSupplier(Organization supplier) {
        this.supplier = supplier;
        return this;
    }

    public Organization getConsumer() {
        return consumer;
    }

    public Action setConsumer(Organization consumer) {
        this.consumer = consumer;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public Action setItem(Item item) {
        this.item = item;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Action setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public ActionType getType() {
        return type;
    }

    public Action setDate(Date date) {
        this.date = date;
        return this;
    }

    public Action setId(int id) {
        this.id = id;
        return this;
    }

    public Action setType(ActionType type) {
        this.type = type;
        return this;
    }

    public Action setUser(User user) {
        this.user = user;
        return this;
    }
}
