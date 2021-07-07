package com.cleanandgo.models;

import java.util.TreeMap;

public class PaymentType
{
	public static TreeMap<Integer, String> paymentType;
	
	static 
	{
		paymentType = new TreeMap<Integer, String>()
		{
			{
				put(1, "Cash");
				put(2, "Credit");
				put(3,"Bitcoin");
			}
		};
	}
	
	public int PaymentTypeID;
	public String TypeName;
	
	public PaymentType(int paymentId)
	{
		this(paymentId, paymentType.get(paymentId));
	}
	
	public PaymentType(int paymentTypeID, String typeName) 
	{
		this.PaymentTypeID = paymentTypeID;
		this.TypeName = typeName;
	}
	
	public PaymentType(PaymentType other)
	{
		this(other.PaymentTypeID, other.TypeName);
	}
}
