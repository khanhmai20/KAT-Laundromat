package com.cleanandgo.models;

import java.util.List;

public class Customer
{
	private int customerID;
	private CustomerProfile customerProfile;
	private List<CustomerTransaction> customerTransactions;
	private List<Address> customerAddresses;
	private List<Contact> customerContacts;
	
	public Customer(CustomerProfile customerProfile, List<CustomerTransaction> customerTransactions, List<Contact> customerContacts, List<Address> customerAddresses)
	{
		this(0,customerProfile,customerTransactions,customerContacts,customerAddresses);
	}
	public Customer(int customerID, CustomerProfile customerProfile, List<CustomerTransaction> customerTransactions, List<Contact> customerContacts, List<Address> customerAddresses)
	{
		this.setCustomerID(customerID);
		this.setCustomerProfile(customerProfile);
		this.setCustomerTransactions(customerTransactions);
		this.setCustomerContacts(customerContacts);
		this.setCustomerAddresses(customerAddresses);
	}

	public int getCustomerID()
	{
		return customerID;
	}
	
	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
	}
	public CustomerProfile getCustomerProfile()
	{	
		return customerProfile;
	}
	
	public void setCustomerProfile(CustomerProfile CustomerProfile)
	{
		this.customerProfile = CustomerProfile;
	}

	public List<CustomerTransaction> getCustomerTransactions() {
		return customerTransactions;
	}

	public void setCustomerTransactions(List<CustomerTransaction> customerTransactions) {
		this.customerTransactions = customerTransactions;
	}
	
	public List<Contact> getCustomerContacts() {
		return customerContacts;
	}

	public void setCustomerContacts(List<Contact> customerContacts) {
		this.customerContacts = customerContacts;
	}
	
	public List<Address> getCustomerAddresses() {
		return customerAddresses;
	}

	public void setCustomerAddresses(List<Address> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}
	
}