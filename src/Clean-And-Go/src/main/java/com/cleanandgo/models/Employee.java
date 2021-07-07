package com.cleanandgo.models;

import java.util.List;

public class Employee
{
	private int employeeID;
	private EmployeeProfile employeeProfile;
	private EmployeeUser employeeUser;
	private List<Address> addresses;
	private List<Contact> contacts;
	private List<WorkSchedule> schedules;
	
	public Employee(EmployeeProfile employeeProfile, EmployeeUser employeeUser, List<Address> addresses, List<Contact> contacts, List<WorkSchedule> schedules)
	{
		this(0, employeeProfile, employeeUser, addresses, contacts, schedules);
	}
	
	public Employee(int employeeID, EmployeeProfile employeeProfile, EmployeeUser employeeUser, List<Address> addresses, List<Contact> contacts, List<WorkSchedule> schedules) 
	{
		this.employeeID = employeeID;
		this.employeeProfile = employeeProfile;
		this.employeeUser = employeeUser;
		this.addresses = addresses;
		this.contacts = contacts;
		this.schedules = schedules;
	}

	public int getEmployeeID()
{
		return employeeID;
	}

	public void setEmployeeID(int employeeID)
	{
		this.employeeID = employeeID;
	}

	public EmployeeProfile getEmployeeProfile()
	{
		return employeeProfile;
	}

	public void setEmployeeProfile(EmployeeProfile employeeProfile)
	{
		this.employeeProfile = employeeProfile;
	}

	public EmployeeUser getEmployeeUser()
	{
		return employeeUser;
	}

	public void setEmployeeUser(EmployeeUser employeeUser)
	{
		this.employeeUser = employeeUser;
	}

	public List<Address> getAddresses() 
	{
		return addresses;
	}

	public void setAddresses(List<Address> addresses) 
	{
		this.addresses = addresses;
	}

	public List<Contact> getContacts() 
	{
		return contacts;
	}

	public void setContacts(List<Contact> contacts) 
	{
		this.contacts = contacts;
	}

	public List<WorkSchedule> getSchedules() 
	{
		return schedules;
	}

	public void setSchedules(List<WorkSchedule> schedules)
	{
		this.schedules = schedules;
	}
}
