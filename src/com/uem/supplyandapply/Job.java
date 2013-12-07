package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//Notes: David changed the value of hashmap from arraylist of string to 
//ApplianceStateContainer. Which renders PARTS obsolete since they are
//cotained in ApplianceStateContainer

public class Job implements Serializable {

	// The customer for which this job is for
	private Customer customer;
	// Maps the appliance-groups broken to a list of where they are located within the customer site
	private HashMap<String, ApplianceStateContainer> broken;
	// Parts estimation or updates
	private HashMap<String, Integer> parts;
	// Status on finishing
	private Timeframe t;
	// Display a string with name and address on the job page
	private String display;

	Job(Customer c, HashMap<String, ApplianceStateContainer> broken, HashMap<String, Integer> parts) {
		this.customer = c;
		this.broken = broken;
		t = Timeframe.CURRENT;
		this.parts = parts;
		this.display = c.getName() + ": " + c.getAddress();
	}

	/**
	 * @return the customer for this job
	 */
	public Customer getC() {
		return customer;
	}

	/**
	 * @return the broken appliances and their locations for this job
	 */
	public HashMap<String, ApplianceStateContainer> getBroken() {
		return broken;
	}

	/**
	 * @return the timeframe for this job
	 */
	public Timeframe getT() {
		return t;
	}

	/**
	 * @return the display name and address for this job
	 */
	public String getDisplay() {
		return display;
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(customer);
		stream.writeObject(broken);
		stream.writeObject(parts);
		stream.writeObject(t);
		stream.writeObject(display);
	}

	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		customer = (Customer) stream.readObject();
		broken = (HashMap<String, ApplianceStateContainer>) stream.readObject();
		parts = (HashMap<String, Integer>) stream.readObject();
		t = (Timeframe) stream.readObject();
		display = (String) stream.readObject();
	}

}
