package com.cleanandgo.models;

import java.util.TreeMap;

public class AssetType 
{
	public static TreeMap<Integer, String[]> assetTypes;
	
	static 
	{
		assetTypes = new TreeMap<Integer, String[]>()
		{
			{
				put(1, new String[]{"Equipment", "Machines including washers, dryers, trolleys etc." });
				put(2, new String[]{"Cleaning Supplies", "Cleaning supplies"});
			}
		};
	}
	
	public int AssetTypeID;
	public String TypeName;
	public String TypeDescription;

	public AssetType(int assetTypeID) 
	{
		this(assetTypeID, (assetTypes.get(assetTypeID))[0], (assetTypes.get(assetTypeID))[1]);
	}
	
	public AssetType(int assetTypeID, String typeName, String typeDescription)
	{
		this.AssetTypeID = assetTypeID;
		this.TypeName = typeName;
		this.TypeDescription = typeDescription;
	}
}


