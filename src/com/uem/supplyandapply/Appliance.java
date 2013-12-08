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
    private Progress progress;

    public Appliance(String name, int drawableResource) {
        this.name = name;
        this.drawableResource = drawableResource;
        progress = Progress.NOT_STARTED;
    }


    public Appliance(String name, int drawableResource, ArrayList<SupplyPart> partsList) {
    	this.name = name;
    	this.drawableResource = drawableResource;
    	this.partsList = partsList;
    }
    
    public String getName() {
        return name;
    }


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the drawableResource
	 */
	public int getDrawableResource() {
		return drawableResource;
	}

	/**
	 * @param drawableResource the drawableResource to set
	 */
	public void setDrawableResource(int drawableResource) {
		this.drawableResource = drawableResource;
	}

	/**
	 * @return the partsList
	 */
	public ArrayList<SupplyPart> getPartsList() {
		return partsList;
	}

	/**
	 * @param partsList the partsList to set
	 */
	public void setPartsList(ArrayList<SupplyPart> partsList) {
		this.partsList = partsList;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	
	/**
	 * @param get the progress 
	 */
	public Progress getProgress() {
		return progress;
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
