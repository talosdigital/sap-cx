package com.talos.meetup.lambdaMethodReferences.data;

import java.util.Date;


public class FutureStockData
{
	private Date date;

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		FutureStockData that = (FutureStockData) o;

		return getDate() != null ? getDate().equals(that.getDate()) : that.getDate() == null;
	}

	@Override
	public int hashCode()
	{
		return getDate() != null ? getDate().hashCode() : 0;
	}
}
