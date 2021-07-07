package com.cleanandgo.models;
import java.time.LocalDate;
import java.util.Date;

public class AssetMaintenanceSchedule 
{
	public Asset Asset;
	public LocalDate NextMaintenance;
	
	public AssetMaintenanceSchedule(Asset asset, LocalDate nextMaintenance) 
	{
		this.Asset = asset;
		this.NextMaintenance = nextMaintenance;
	}
}
