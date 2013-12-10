package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(ArrayList<Appliance> appliances) {
        this.appliances = appliances;
    }

    public int getNotFinished() {
    	int notFinished = 0;
    	for (Appliance appliance : appliances) {
    		if (appliance.getProgress() != Progress.COMPLETED) {
    			notFinished++;
    		}
    	}
    	
    	return notFinished;
    }
    
    public ArrayList<SupplyPart> getInitialPartsList() {
        ArrayList<SupplyPart> partsList = appliance.getPartsList();
        if (!partsList.isEmpty()) {
            for (SupplyPart supplyPart : partsList) {
                supplyPart.setCount(supplyPart.getCount() * count);
            }
        }
        return partsList;
    }
    
    public ArrayList<SupplyPart> getPartsList() {
    	HashMap<String, SupplyPart> parts = new HashMap<String, SupplyPart>();
    	for (Appliance appliance : appliances) {
    		for (SupplyPart supplyPart : appliance.getPartsList()) {
    			if (parts.containsKey(supplyPart.getName())) {
    				SupplyPart foundPart = parts.get(supplyPart.getName());
    				int oldCount = foundPart.getCount();
    				foundPart.setCount(oldCount + supplyPart.getCount());
    			} else {
    				parts.put(supplyPart.getName(), new SupplyPart(supplyPart.getCount(), supplyPart.getName()));
    			}
    		}
    	}
    	ArrayList<SupplyPart> result = new ArrayList<SupplyPart>();
    	for (SupplyPart v : parts.values()) {
    		result.add(v);
    	}
    	return result;
    }
    
    public ArrayList<SupplyPart> getUnfinishedPartsList() {
    	HashMap<String, SupplyPart> parts = new HashMap<String, SupplyPart>();
    	for (Appliance appliance : appliances) {
    		if (appliance.getProgress() != Progress.COMPLETED) {
    			for (SupplyPart supplyPart : appliance.getPartsList()) {
    				if (parts.containsKey(supplyPart.getName())) {
    					SupplyPart foundPart = parts.get(supplyPart.getName());
    					int oldCount = foundPart.getCount();
    					foundPart.setCount(oldCount + supplyPart.getCount());
    				} else {
    					parts.put(supplyPart.getName(), new SupplyPart(supplyPart.getCount(), supplyPart.getName()));
    				}
    			}
    		}
    	}
    	ArrayList<SupplyPart> result = new ArrayList<SupplyPart>();
    	for (SupplyPart v : parts.values()) {
    		result.add(v);
    	}
    	return result;
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
