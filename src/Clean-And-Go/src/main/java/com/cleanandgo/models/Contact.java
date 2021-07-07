package com.cleanandgo.models;

public class Contact 
{
	public ContactType ContactType;
	public String PhoneNumber;
	
	public Contact(ContactType contactType, String phoneNumber) 
	{
		this.ContactType = contactType;
		this.PhoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() 
	{
		return String.format("%s: %s", ContactType.TypeName, PhoneNumber);
	}
}
