package com.talos.meetup.model;

import java.util.List;


public class AbstractOrderModel
{
	private String code;
	private List<AbstractOrderEntryModel> entries;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public List<AbstractOrderEntryModel> getEntries()
	{
		return entries;
	}

	public void setEntries(List<AbstractOrderEntryModel> entries)
	{
		this.entries = entries;
	}
}
