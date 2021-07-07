package com.cleanandgo.models;

import java.util.TreeMap;

public class ContactType
{
	public static TreeMap<Integer, String> contactTypes;
	
	static 
	{
		contactTypes = new TreeMap<Integer, String>()
		{
			{
				put(1, "Home");
				put(2, "Cell");
				put(3, "Office");
				put(4, "Fax");
			}
			
		};

	}
	
	public int ContactTypeID;
	public String TypeName;
	
	public ContactType(int contactTypeID)
	{
		this(contactTypeID, contactTypes.get(contactTypeID));
	}
	
	public ContactType(int contactTypeID, String typeName) 
	{
		this.ContactTypeID = contactTypeID;
		this.TypeName = typeName;
	}
}
