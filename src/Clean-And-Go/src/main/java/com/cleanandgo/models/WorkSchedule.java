package com.cleanandgo.models;

import java.time.*;
import java.util.Date;

public class WorkSchedule 
{
	public LocalDate WorkDate;
	public LocalTime StartTime;
	public LocalTime EndTime;
	public LocalTime HoursWorked;
	
	public WorkSchedule(LocalDate workDate, LocalTime startTime, LocalTime endTime, LocalTime hoursWorked) 
	{
		this.WorkDate = workDate;
		this.StartTime = startTime;
		this.EndTime = endTime;
		this.HoursWorked = hoursWorked;
	}
	
	@Override
	public String toString() 
	{
		return String.format
		(
				"Date: %s, Start Time: %s, End Time: %s, Hours Worked: %s",
				WorkDate,
				StartTime,
				EndTime,
				HoursWorked
		);
	}
	
}
