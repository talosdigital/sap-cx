package com.talos.meetup.model;

public class AbstractOrderEntryModel
{
	private AddressModel paymentAddress;
	private AddressModel deliveryAddress;

	public AddressModel getPaymentAddress()
	{
		return paymentAddress;
	}

	public void setPaymentAddress(AddressModel paymentAddress)
	{
		this.paymentAddress = paymentAddress;
	}

	public AddressModel getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressModel deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}
}
