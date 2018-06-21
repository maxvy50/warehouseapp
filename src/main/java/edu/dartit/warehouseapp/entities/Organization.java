package edu.dartit.warehouseapp.entities;

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
