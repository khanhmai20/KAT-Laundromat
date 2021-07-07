package com.cleanandgo.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a transaction struct. Contains information about a transaction.
 */
public class CustomerTransaction 
{
	public int CustomerTransactionID;
	public int CustomerID;
	public TransactionDetail TransactionDetail;
	public String Review;
	public Employee Employee;
	public Service Service;
	
	public CustomerTransaction(int customerID, TransactionDetail transactionDetail, String review, Employee employee, Service service) { 
		this(0, customerID, transactionDetail, review, employee, service);
	}
	
	public CustomerTransaction(int customerTransactionID, int customerID, TransactionDetail transactionDetail, String review, Employee employee, Service service) 
	{
		this.CustomerTransactionID = customerTransactionID;
		this.CustomerID = customerID;
		this.TransactionDetail = transactionDetail;
		this.Review = review;
		this.Employee = employee;
		this.Service = service;
	}
	
}
