package edu.dartit.warehouseapp.entities;

import edu.dartit.warehouseapp.utils.dao.DAOException;
import edu.dartit.warehouseapp.utils.dao.OrgsHasItemsDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by vysokov-mg on 18.06.2018.
 */
public class Organization {

    private String name;
    private String region;
    private String address;

    public Organization(String org_name, String region, String address) {
        this.name = org_name;
        this.region = region;
        this.address = address;
    }


    public void post(Item item, int amount) throws DAOException {
        OrgsHasItemsDAO.add(this, item, amount);
    }

    public void sell(Item item, int amount) throws DAOException {
        OrgsHasItemsDAO.pickUp(this, item, amount);
    }

    public void replace(Organization consumer, Item item, int amount) throws DAOException {
        OrgsHasItemsDAO.pickUp(this, item, amount);
        OrgsHasItemsDAO.add(consumer, item, amount);
    }

    public List<Record> getItemJournal() throws DAOException {
        return OrgsHasItemsDAO.getItemsFor(this);
    }

    public void setName(String org_name) {
        this.name = org_name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name;
    }
}
