package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;

public class Job {

	// The customer for which this job is for
	private Customer c;
	// Maps the appliance-groups broken to how list of where they are located within the customer site
	private HashMap<String, ArrayList<String>> broken;
	// Parts estimation or updates
	private HashMap<String, Integer> parts;
	// Status on finishing
	private Timeframe t;
	// Display a string with name and address on the job page
	private String display;
	
	Job(Customer c, HashMap<String, ArrayList<String>> broken, HashMap<String, Integer> parts) {
		this.c = c;
		this.broken = broken;
		t = Timeframe.CURRENT;
		this.parts = parts;
		this.display = c.getName() + ": " + c.getAddress();
	}

	/**
	 * @return the customer for this job
	 */
	public Customer getC() {
		return c;
	}

	/**
	 * @return the broken appliances and their locations for this job
	 */
	public HashMap<String, ArrayList<String>> getBroken() {
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
}
