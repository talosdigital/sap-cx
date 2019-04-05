package com.talos.meetup.optionals;

import com.talos.meetup.model.AddressModel;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class Optional2Test
{
	private static final String DEFAULT_VALUE = "default value";
	private static final String STREET_NAME = "316 West";

	@Test
	public void testNotNull()
	{
		AddressModel addressModel = new AddressModel();
		addressModel.setStreetName(STREET_NAME);

		String streetName = getStreetName(addressModel);

		assertNotNull(streetName);
		assertEquals(STREET_NAME, streetName);
	}

	@Test
	public void testNull()
	{
		String streetName = getStreetName(null);

		assertNotNull(streetName);
		assertEquals(DEFAULT_VALUE, streetName);
	}

	@Test
	public void testEmpty()
	{
		AddressModel addressModel = new AddressModel();
		addressModel.setStreetName(StringUtils.EMPTY);

		String streetName = getStreetName(addressModel);

		assertNotNull(streetName);
		assertEquals(DEFAULT_VALUE, streetName);
	}

	//TODO refactor this one!
	private String getStreetName(AddressModel addressModel)
	{
//		if (addressModel == null)
//		{
//			return getDefault();
//		}
//		String streetName = addressModel.getStreetName();
//		if (StringUtils.isBlank(streetName))
//		{
//			return getDefault();
//		}
//		return streetName;
		return Optional.ofNullable(addressModel)
				.map(AddressModel::getStreetName)
				.filter(StringUtils::isNotBlank)
				.orElseGet(this::getDefault);
	}

	/**
	 * It is a CPU consuming process!
	 * @return
	 */
	private String getDefault()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
		}
		return DEFAULT_VALUE;
	}
}
