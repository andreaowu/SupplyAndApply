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
	
	Job(Customer c, HashMap<String, ArrayList<String>> broken, HashMap<String, Integer> parts) {
		this.c = c;
		this.broken = broken;
		t = Timeframe.CURRENT;
		this.parts = parts;
	}

	/**
	 * @return the c
	 */
	public Customer getC() {
		return c;
	}

	/**
	 * @return the broken
	 */
	public HashMap<String, ArrayList<String>> getBroken() {
		return broken;
	}

	/**
	 * @return the t
	 */
	public Timeframe getT() {
		return t;
	}
	
}
