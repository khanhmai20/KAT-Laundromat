package com.cleanandgo.models;

import java.util.TreeMap;

public class ServiceType 
{
	public static TreeMap<Integer, String[]> serviceTypes;
	
	static 
	{
		serviceTypes = new TreeMap<Integer, String[]>()
		{
			{
				put(1, new String[]{"Washer", null});
				put(2, new String[]{"Drier", null});
				put(3, new String[]{"Drop Off", null});
				put(4, new String[]{"Dry Cleaning", null});
			}
		};
	}
	
	public int ServiceTypeID;
	public String TypeName;
	public String TypeDescription;

	public ServiceType(int serviceTypeID) 
	{
		this(serviceTypeID, (serviceTypes.get(serviceTypeID))[0], (serviceTypes.get(serviceTypeID))[1]);
	}
	
	public ServiceType(int serviceTypeID, String typeName, String typeDescription)
	{
		this.ServiceTypeID = serviceTypeID;
		this.TypeName = typeName;
		this.TypeDescription = typeDescription;
	}
}