package com.cleanandgo.models;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerProfile {
		public Name customerName;
		public String customerEmail;
		
		public CustomerProfile(Name customerName, String customerEmail) 
		{
			this.customerName = customerName;
			this.customerEmail = customerEmail;
		}
	}
