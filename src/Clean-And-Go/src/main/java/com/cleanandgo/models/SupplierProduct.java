package com.cleanandgo.models;

public class SupplierProduct {
	public int SupplierProductID;
	public int SupplierID;
	public int ProductID;

	public SupplierProduct(int supplierProductID, int supplierID, int productID){
		this.SupplierProductID = supplierProductID;
		this.SupplierID = supplierID;
		this.ProductID = productID;
	}
}
