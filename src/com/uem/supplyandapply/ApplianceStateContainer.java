package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/15/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplianceStateContainer implements Serializable {

    private Appliance appliance;
    private int count;
    private int notFinished;

    public ApplianceStateContainer(Appliance appliance, int count) {
        this.appliance = appliance;
        this.count = count;
        this.notFinished = 0;
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
    
    public void setNotFinished(int count) {
        this.notFinished = count;
    }
    
    public int getNotFinished() {
        return this.notFinished;
    }

    public ArrayList<SupplyPart> getPartsList() {
        ArrayList<SupplyPart> partsList = appliance.getPartsList();
        for (SupplyPart supplyPart : partsList) {
            supplyPart.setCount(supplyPart.getCount() * count);
        }
        return partsList;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(appliance);
        stream.writeInt(count);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        appliance = (Appliance) stream.readObject();
        count = stream.readInt();
    }}
