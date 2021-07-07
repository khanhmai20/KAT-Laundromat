package com.cleanandgo.models;

import java.time.LocalDate;

public class AssetManagement 
{
	public int AssetManagementID;
	public Employee Employee;
	public Asset Asset;
	public LocalDate UseDate;
	
	public AssetManagement(Employee employee, Asset asset, LocalDate useDate)
	{
		this(0, employee, asset, useDate);
	}
	
	public AssetManagement(int assetManagementID, Employee employee, Asset asset, LocalDate useDate)
	{
		this.AssetManagementID = assetManagementID;
		this.Employee = employee;
		this.Asset = asset;
		this.UseDate = useDate;
	}
}

