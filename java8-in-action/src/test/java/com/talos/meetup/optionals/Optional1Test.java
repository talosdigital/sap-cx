package com.talos.meetup.optionals;

import com.talos.meetup.model.AbstractOrderEntryModel;
import com.talos.meetup.model.AddressModel;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


public class Optional1Test
{

	private static final String DEFAULT_ADDRESS_NAME = "default address name";
	private static final String STREET_NAME = "316 West";

	@Test
	public void testNull()
	{
		AbstractOrderEntryModel abstractOrderEntryModel = create(Option.NULL);

		String value = getAddressName(abstractOrderEntryModel);

		assertNull(abstractOrderEntryModel);
		assertEquals(DEFAULT_ADDRESS_NAME, value);
	}

	@Test
	public void testNullAddressModel()
	{
		AbstractOrderEntryModel abstractOrderEntryModel = create(Option.DELIVERY_ADDRESS_NULL);

		String value = getAddressName(abstractOrderEntryModel);

		assertNotNull(abstractOrderEntryModel);
		assertNull(abstractOrderEntryModel.getDeliveryAddress());
		assertEquals(DEFAULT_ADDRESS_NAME, value);
	}

	@Test
	public void testNullStreetName()
	{
		AbstractOrderEntryModel abstractOrderEntryModel = create(Option.STREET_NAME_NULL);

		String value = getAddressName(abstractOrderEntryModel);

		assertNotNull(abstractOrderEntryModel);
		assertNotNull(abstractOrderEntryModel.getDeliveryAddress());
		assertNull(abstractOrderEntryModel.getDeliveryAddress().getStreetName());
		assertEquals(DEFAULT_ADDRESS_NAME, value);
	}

	@Test
	public void testEmptyStreetName()
	{
		AbstractOrderEntryModel abstractOrderEntryModel = create(Option.STREET_NAME_EMPTY);

		String value = getAddressName(abstractOrderEntryModel);

		assertNotNull(abstractOrderEntryModel);
		assertNotNull(abstractOrderEntryModel.getDeliveryAddress());
		assertThat(abstractOrderEntryModel.getDeliveryAddress().getStreetName(), Matchers.blankString());
		assertEquals(DEFAULT_ADDRESS_NAME, value);
	}

	@Test
	public void testComplete()
	{
		AbstractOrderEntryModel abstractOrderEntryModel = create(Option.COMPLETE);

		String value = getAddressName(abstractOrderEntryModel);

		assertNotNull(abstractOrderEntryModel);
		assertNotNull(abstractOrderEntryModel.getDeliveryAddress());
		assertThat(abstractOrderEntryModel.getDeliveryAddress().getStreetName(), Matchers.not(Matchers.blankOrNullString()));
		assertEquals(STREET_NAME, value);
	}

	//TODO refactor this one!
	private String getAddressName(AbstractOrderEntryModel abstractOrderEntryModel)
	{
//		if (abstractOrderEntryModel == null)
//		{
//			return DEFAULT_ADDRESS_NAME;
//		}
//		AddressModel addressModel = abstractOrderEntryModel.getDeliveryAddress();
//		if (addressModel == null)
//		{
//			return DEFAULT_ADDRESS_NAME;
//		}
//
//		String streetName = addressModel.getStreetName();
//		if (StringUtils.isBlank(streetName))
//		{
//			return DEFAULT_ADDRESS_NAME;
//		}
//		return streetName;
		return Optional.ofNullable(abstractOrderEntryModel)
				.map(AbstractOrderEntryModel::getDeliveryAddress)
				.map(AddressModel::getStreetName)
				.filter(StringUtils::isNotBlank)
				.orElse(DEFAULT_ADDRESS_NAME);
	}

	private enum Option
	{
		NULL,
		DELIVERY_ADDRESS_NULL,
		STREET_NAME_NULL,
		STREET_NAME_EMPTY,
		COMPLETE
	}

	private AbstractOrderEntryModel create(Option option)
	{
		if (option == Option.NULL)
		{
			return null;
		}
		AbstractOrderEntryModel abstractOrderEntryModel = new AbstractOrderEntryModel();
		if (option == Option.DELIVERY_ADDRESS_NULL)
		{
			return abstractOrderEntryModel;
		}
		AddressModel addressModel = new AddressModel();
		abstractOrderEntryModel.setDeliveryAddress(addressModel);
		if (option == Option.STREET_NAME_NULL)
		{
			return abstractOrderEntryModel;
		}
		if (option == Option.STREET_NAME_EMPTY)
		{
			addressModel.setStreetName(StringUtils.EMPTY);
			return abstractOrderEntryModel;
		}
		if (option == Option.COMPLETE)
		{
			addressModel.setStreetName(STREET_NAME);
		}
		return abstractOrderEntryModel;
	}


}
