package com.cleanandgo.models;

import java.math.BigDecimal;

public class Product {
	public int ProductID;
	public String ProductName;
	public BigDecimal ListPrice;

	public Product(int productID, String productName, BigDecimal listPrice) {
		this.ProductID = productID;
		this.ProductName = productName;
		this.ListPrice = listPrice;
	}
}
