package com.cleanandgo.models;

import java.math.BigDecimal;

public class SupplierTransaction {
	public int SupplierTransactionID;
	public int SupplierID;
	public TransactionDetail SupplierTransactionDetail;
	public String TransactionDescription;
	public int Order;
	public BigDecimal UnitPrice;
	
	public SupplierTransaction(int supplierTransactionID, int supplierID, TransactionDetail supplierTransactionDetail, String transactionDescription, int order, BigDecimal unitPrice) {
		this.SupplierTransactionID = supplierTransactionID;
		this.SupplierID = supplierID;
		this.SupplierTransactionDetail = supplierTransactionDetail;
		this.TransactionDescription = transactionDescription;
		this.Order = order;
		this.UnitPrice = unitPrice;
	}
}
