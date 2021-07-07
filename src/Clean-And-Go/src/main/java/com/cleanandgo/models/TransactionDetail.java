package com.cleanandgo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents information on the transaction.
 */
public class TransactionDetail
{
	public LocalDate BillingDate;
	public LocalDate ShippingDate;
	public LocalDate DueDate;
	public BigDecimal AmountCharged;
	public BigDecimal CurrentBalance;
	public PaymentType PaymentType;
	
	public TransactionDetail(LocalDate billingDate, LocalDate shippingDate, LocalDate dueDate, BigDecimal amountCharged, BigDecimal currentBalance, PaymentType paymentType) 
	{
		this.BillingDate = billingDate;
		this.ShippingDate = shippingDate;
		this.DueDate = dueDate;
		this.AmountCharged = amountCharged;
		this.CurrentBalance = currentBalance;
		this.PaymentType = paymentType;
	}
}
