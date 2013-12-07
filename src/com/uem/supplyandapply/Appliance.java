package com.uem.supplyandapply;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Appliance implements Serializable {

    private String name;
    private int drawableResource;
    private ArrayList<SupplyPart> partsList;

    public Appliance(String name, int drawableResource) {
        this.name = name;
        this.drawableResource = drawableResource;
    }

    public Appliance(String name, int drawableResource, ArrayList<SupplyPart> partsList) {
    	this.name = name;
    	this.drawableResource = drawableResource;
    	this.partsList = partsList;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public ArrayList<SupplyPart> getPartsList() {
        return partsList;
    }

    public void setPartsList(ArrayList<SupplyPart> partsList) {
        this.partsList = partsList;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(name);
        stream.writeInt(drawableResource);
        stream.writeObject(partsList);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        drawableResource = stream.readInt();
        partsList = (ArrayList<SupplyPart>) stream.readObject();
    }
}
