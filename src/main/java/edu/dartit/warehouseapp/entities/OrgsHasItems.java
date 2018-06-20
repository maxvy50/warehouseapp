package edu.dartit.warehouseapp.entities;

/**
 * Created by vysokov-mg on 19.06.2018.
 */
public class OrgsHasItems {

    private String orgName;
    private String itemName;
    private int amount;

    public OrgsHasItems(String orgName, String itemName, int amount) {
        this.orgName = orgName;
        this.itemName = itemName;
        this.amount = amount;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.itemName + "[x" + this.amount + "] на складе " + this.orgName;
    }
}
