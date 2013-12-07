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

	private ArrayList<Appliance> appliances;
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

    public int getNotFinished() {
    	/*
    	for (Appliance appliance : appliances) {
    		//TODO-THIS
    	}
    	*/
    	return count;
    }
    
    public ArrayList<SupplyPart> getInitialPartsList() {
        ArrayList<SupplyPart> partsList = appliance.getPartsList();
        for (SupplyPart supplyPart : partsList) {
            supplyPart.setCount(supplyPart.getCount() * count);
        }
        return partsList;
    }
    
    public ArrayList<SupplyPart> getPartsList() {
    	//TODO WRITE THIS
    	return new ArrayList<SupplyPart>();
    }

    public void generateAppliances() {
    	this.appliances = new ArrayList<Appliance>(count);
    	for (int i = 0; i < count; i++) {
    		appliances.add(new Appliance(appliance.getName(),
    				appliance.getDrawableResource(), appliance.getPartsList()));
    	}
    }
    
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(appliances);
    	stream.writeObject(appliance);
        stream.writeInt(count);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        appliances = (ArrayList<Appliance>) stream.readObject();
    	appliance = (Appliance) stream.readObject();
        count = stream.readInt();
    }
}
