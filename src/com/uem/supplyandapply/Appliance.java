package com.uem.supplyandapply;

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
    private String issues;
    private String location;
    private String hiddenId;
  
    public Appliance(String name, int drawableResource) {
        this.name = name;
        this.drawableResource = drawableResource;
        this.partsList = new ArrayList<SupplyPart>();
        this.progress = Progress.NOT_STARTED;
        this.issues = "";
        this.location = "";
    }


    public Appliance(String name, int drawableResource, ArrayList<SupplyPart> partsList) {
    	this.name = name;
    	this.drawableResource = drawableResource;
    	this.partsList = partsList;
        this.progress = Progress.NOT_STARTED;
        this.issues = "";
        this.location = "";
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
	
	public Progress getProgress() {
		return progress;
	}

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(name);
        stream.writeInt(drawableResource);
        stream.writeObject(partsList);
        stream.writeObject(progress);
        stream.writeObject(issues);
        stream.writeObject(location);
        stream.writeObject(hiddenId);
    }

    public CharSequence getIssues() {
        return issues;
    }

    public void setIssues(CharSequence issues){
        this.issues = (String) issues;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        drawableResource = stream.readInt();
        partsList = (ArrayList<SupplyPart>) stream.readObject();
        progress = (Progress) stream.readObject();
        issues = (String) stream.readObject();
        location = (String) stream.readObject();
        hiddenId = (String) stream.readObject();
    }
}
