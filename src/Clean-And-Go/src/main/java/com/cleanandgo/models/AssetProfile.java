package com.cleanandgo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssetProfile {
	public String AssetName;
	public String AssetBrand;
	public String AssetDescription;
	public String SafetyInformation;
	public int CurrentInventory;
	public LocalDate PurchaseDate;
	public BigDecimal PurchasePrice;
	
	public AssetProfile(String assetName, String assetBrand, String assetDescription, String safetyInformation, int currentInventory, LocalDate purchaseDate, BigDecimal purchasePrice) {
		this.AssetName = assetName;
		this.AssetBrand = assetBrand;
		this.AssetDescription = assetDescription;
		this.SafetyInformation = safetyInformation;
		this.CurrentInventory = currentInventory;
		this.PurchaseDate = purchaseDate;
		this.PurchasePrice = purchasePrice;
	}
}
