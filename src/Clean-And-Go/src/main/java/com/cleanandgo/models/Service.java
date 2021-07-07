package com.cleanandgo.models;

import java.time.LocalTime;

public class Service 
{
	public int ServiceID;
	public ServiceType ServiceType;
	public String ServiceName;
	public String ServiceDescription;
	public double Rate;
	public LocalTime Duration;
	
	public Service(int serviceID, ServiceType serviceType, String serviceName, String serviceDescription, double rate, LocalTime duration) 
	{
		this.ServiceID = serviceID;
		this.ServiceType = serviceType;
		this.ServiceName = serviceName;
		this.ServiceDescription = serviceDescription;
		this.Rate = rate;
		this.Duration = duration;
	}
	public Service(ServiceType serviceType, String serviceName, String serviceDescription, double rate, LocalTime duration) {
		this(0, serviceType, serviceName, serviceDescription, rate, duration);
	}
}
