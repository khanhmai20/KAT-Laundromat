package com.cleanandgo.models;

import java.util.TreeMap;

/**
 * Represents an address type struct.
 */
public class AddressType 
{
	public static TreeMap<Integer, String> addressTypes;
	
	static 
	{
		addressTypes = new TreeMap<Integer, String>()
		{
			{
				put(1, "Billing");
				put(2, "Shipping");
			}
		};
	}
	
	public int AddressTypeID;
	public String TypeName;
	
	public AddressType(int addressTypeID)
	{
		this(addressTypeID, addressTypes.get(addressTypeID));
	}
	
	public AddressType(int addressTypeID, String typeName) 
	{
		this.AddressTypeID = addressTypeID;
		this.TypeName = typeName;
	}
	
	public AddressType(AddressType other)
	{
		this(other.AddressTypeID, other.TypeName);
	}
}
