package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Job implements Serializable {

	// The customer for which this job is for
	private Customer customer;
	// Maps the appliance-groups broken to a list of where they are located within the customer site
	private HashMap<String, ApplianceStateContainer> broken;
	// Status on finishing
	private Timeframe t;
	// Display a string with name and address on the job page
	private String display;
    // Whether or not the Job has been started or not
    private boolean jobStarted;


	Job(Customer c, HashMap<String, ApplianceStateContainer> broken) {
		this.customer = c;
		this.broken = broken;
		t = Timeframe.CURRENT;
		this.display = c.getName() + ": " + c.getAddress();
        this.jobStarted = false;
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

    public boolean isJobStarted() {
        return jobStarted;
    }

    public void setJobStarted(boolean jobStarted) {
        this.jobStarted = jobStarted;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(customer);
		stream.writeObject(broken);
		stream.writeObject(t);
		stream.writeObject(display);
        stream.writeBoolean(jobStarted);
	}

	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		customer = (Customer) stream.readObject();
		broken = (HashMap<String, ApplianceStateContainer>) stream.readObject();
		t = (Timeframe) stream.readObject();
		display = (String) stream.readObject();
        jobStarted = stream.readBoolean();
	}

}
