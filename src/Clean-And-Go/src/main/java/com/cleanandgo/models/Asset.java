package com.cleanandgo.models;

import java.util.List;

public class Asset {
	private int AssetID;
	private AssetType AssetType;
	private AssetProfile AssetProfile;
	private List<AssetManagement> AssetManagements;
	private List<AssetMaintenanceSchedule> AssetMaintenanceSchedules; 
	
	public Asset(AssetType assetType, AssetProfile assetProfile, List<AssetManagement> assetManagements, List<AssetMaintenanceSchedule> assetMaintenanceSchedules)
	{
		this(0, assetType, assetProfile, assetManagements, assetMaintenanceSchedules);
	}
	
	public Asset(int assetID, AssetType assetType, AssetProfile assetProfile, List<AssetManagement> assetManagements, List<AssetMaintenanceSchedule> assetMaintenanceSchedules) 
	{
		this.setAssetID(assetID);
		this.setAssetType(assetType);
		this.setAssetProfile(assetProfile);
		this.setAssetManagements(assetManagements);
		this.setAssetMaintenanceSchedules(assetMaintenanceSchedules);
	}

	public int getAssetID() 
	{
		return AssetID;
	}

	public void setAssetID(int assetID) 
	{
		AssetID = assetID;
	}

	public AssetType getAssetType() 
	{
		return AssetType;
	}

	public void setAssetType(AssetType assetType)
	{
		AssetType = assetType;
	}

	public AssetProfile getAssetProfile()
	{
		return AssetProfile;
	}

	public void setAssetProfile(AssetProfile assetProfile)
	{
		AssetProfile = assetProfile;
	}

	public List<AssetManagement> getAssetManagements()
	{
		return AssetManagements;
	}

	public void setAssetManagements(List<AssetManagement> assetManagements)
	{
		AssetManagements = assetManagements;
	}

	public List<AssetMaintenanceSchedule> getAssetMaintenanceSchedules() 
	{
		return AssetMaintenanceSchedules;
	}

	public void setAssetMaintenanceSchedules(List<AssetMaintenanceSchedule> assetMaintenanceSchedules) 
	{
		AssetMaintenanceSchedules = assetMaintenanceSchedules;
	}
}
