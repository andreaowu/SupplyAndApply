package com.uem.supplyandapply;

public class Customer {

	// The name of the customer
	private String name;
	// The address of the customer
	private String address;
	
	Customer(String name, String address) {
		this.name = name;
		this.address = address;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
}
