package com.cleanandgo.models;

import java.util.List;

public class Supplier {
	private int SupplierID;
	private String SupplierName;
	private List<SupplierTransaction> SupplierTransactions;
	private List<Address> SupplierAddresses;
	private List<Contact> SupplierContacts;
	private List<Product> Products;
	
	public Supplier(int supplierID, String supplierName, List<SupplierTransaction> supplierTransactions, List<Address> supplierAddresses, List<Contact> supplierContacts, List<Product> products){
		this.SupplierID = supplierID;
		this.SupplierName = supplierName;
		this.SupplierTransactions = supplierTransactions;
		this.SupplierAddresses = supplierAddresses;
		this.SupplierContacts = supplierContacts;
		this.Products = products;
	}
	public int getSupplierID()
	{
		return SupplierID;
	}
	
	public void setCustomerID(int supplierID)
	{
		this.SupplierID = supplierID;
	}
	
	public String getSupplierName()
	{
		return SupplierName;
	}
	
	public void setSupplierName(String supplierName)
	{
		this.SupplierName = supplierName;
	}
	
	public List<SupplierTransaction> getSupplierTransactions() {
		return SupplierTransactions;
	}

	public void setSupplierTransactions(List<SupplierTransaction> supplierTransactions) {
		SupplierTransactions = supplierTransactions;
	}

	public List<Address> getSupplierAddresses() {
		return SupplierAddresses;
	}

	public void setSupplierAddresses(List<Address> supplierAddresses) {
		SupplierAddresses = supplierAddresses;
	}

	public List<Contact> getSupplierContacts() {
		return SupplierContacts;
	}

	public void setSupplierContacts(List<Contact> supplierContacts) {
		SupplierContacts = supplierContacts;
	}
	
	public List<Product> getProducts() {
		return Products;
	}

	public void setProducts(List<Product> products) {
		Products = products;
	}
}
