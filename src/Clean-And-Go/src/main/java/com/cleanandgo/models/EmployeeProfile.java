package com.cleanandgo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an employee profile struct.
 */
public class EmployeeProfile
{
	public Name employeeName;
	public char gender;
	public LocalDate employmentDate;
	public String employeePosition;
	public BigDecimal salary;
	
	public EmployeeProfile(Name employeeName, char gender, LocalDate employmentDate, String employeePosition, BigDecimal salary) 
	{
		this.employeeName = employeeName;
		this.gender = gender;
		this.employmentDate = employmentDate;
		this.employeePosition = employeePosition;
		this.salary = salary;
	}
}
