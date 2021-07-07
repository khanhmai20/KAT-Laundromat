package com.cleanandgo.models;

public class EmployeeUser 
{
	public String UserName;
	public String UserPassword;
	
	/**
	 * Constructs a Employee User given the employee id, user name and password.
	 * @param employeeID The ID of the employee.
	 * @param userName The employee's user name.
	 * @param userPassword The employee's password.
	 */
	public EmployeeUser(String userName, String userPassword)
	{
		this.UserName = userName;
		this.UserPassword = userPassword;
	}
}
