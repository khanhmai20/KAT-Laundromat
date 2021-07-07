package com.cleanandgo.models;

/**
 * Represents an address struct.
 */
public class Address
{
	public AddressType AddressType;
	public String Line1;
	public String Line2;
	public String City;
	public State State;
	public String ZipCode;
	
	public Address(AddressType addressType, String line1, String city, State state, String zipcode) 
	{
		this(addressType, line1, null, city, state, zipcode);
	}
	
	public Address(AddressType addressType, String line1, String line2, String city, State state, String zipcode) 
	{
		this.AddressType = addressType;
		this.Line1 = line1;
		this.Line2 = line2;
		this.City = city;
		this.State = state;
		this.ZipCode = zipcode;
	}
	
	public Address(Address other) 
	{
		this(new AddressType(other.AddressType), other.Line1, other.Line2, other.City, other.State, other.ZipCode);
	}
	
	@Override
	public String toString()
	{
		return String.format
		(
				"%s: %s, %s%s, %s, %s", 
				AddressType.TypeName,
				Line1,
				Line2 == null ? "" : Line2 + ", ",
				City,
				State.StateName,
				ZipCode
		);
	}

}
