package com.uem.supplyandapply;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/15/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplianceStateContainer {

    private Appliance appliance;
    private int count;

    public ApplianceStateContainer(Appliance appliance, int count) {
        this.appliance = appliance;
        this.count = count;
    }

    public Appliance getAppliance() {
        return appliance;
    }

    public void setAppliance(Appliance appliance) {
        this.appliance = appliance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
