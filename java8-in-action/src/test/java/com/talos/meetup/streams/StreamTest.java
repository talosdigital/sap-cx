package com.talos.meetup.streams;

import com.talos.meetup.model.AbstractOrderEntryModel;
import com.talos.meetup.model.AbstractOrderModel;
import com.talos.meetup.model.AddressModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.Matchers;
import org.junit.Test;
import static org.junit.Assert.assertThat;


public class StreamTest
{

	private static final String ONE = "1";
	private static final String TWO = "2";
	private static final String THREE = "3";
	private static final String FOUR = "4";
	private static final String FIVE = "5";
	private static final String ORDER_1 = "1234";
	private static final String ORDER_2 = "5678";
	private static final String ZIP_1 = "zip 1";
	private static final String ZIP_2 = "zip 2";
	private static final String ZIP_3 = "zip 3";
	private static final String ZIP_4 = "zip 4";
	private static final String ZIP_5 = "zip 5";

	@Test
	public void testGetAllButTwo()
	{
		List<String> list = createList();
//		List<String> result = new ArrayList<>();
//		for (String element : list)
//		{
//			if (!TWO.equals(element))
//			{
//				result.add(element);
//			}
//		}
		Stream<String> stream = list.stream();
		List<String> result = stream.filter(e -> !TWO.equals(e)).collect(Collectors.toList());

		assertThat(result, Matchers.not(Matchers.empty()));
		assertThat(result, Matchers.hasItems(ONE, THREE, FOUR, FIVE));
	}

	@Test
	public void testConvert()
	{
		List<String> list = createList();
//		List<Integer> result = new ArrayList<>();
//		for (String element : list)
//		{
//			Integer value = Integer.parseInt(element);
//			result.add(value);
//		}

		List<Integer> result = list.stream().map(Integer::parseInt).collect(Collectors.toList());

				assertThat(result, Matchers.not(Matchers.empty()));
		assertThat(result, Matchers.hasItems(1, 2, 3, 4, 5));
	}

	@Test
	public void testEvenNumbers()
	{
		List<String> list = createList();
//		List<Integer> result = new ArrayList<>();
//		for (String element : list)
//		{
//			Integer value = Integer.parseInt(element);
//			if (value % 2 == 0)
//			{
//				result.add(value);
//			}
//		}
		List<Integer> result = list.stream().map(Integer::parseInt)
				.filter(value -> value % 2 == 0)
				.collect(Collectors.toList());

		assertThat(result, Matchers.not(Matchers.empty()));
		assertThat(result, Matchers.hasItems(2, 4));
	}

	@Test
	public void testGetAllZipCodesButZip4()
	{
		List<AbstractOrderModel> orders = createOrders();
//		Set<String> result = new HashSet<>();
//		for (AbstractOrderModel order : orders)
//		{
//			for (AbstractOrderEntryModel entry : order.getEntries())
//			{
//				AddressModel addressModel = entry.getDeliveryAddress();
//				String zipCode = addressModel.getZipCode();
//				if (StringUtils.isNotBlank(zipCode) && !ZIP_4.equals(zipCode))
//				{
//					result.add(zipCode);
//				}
//			}
//		}
		Set<String> result = orders.stream()
				.flatMap(o -> o.getEntries().stream())
				.map(AbstractOrderEntryModel::getDeliveryAddress)
				.filter(Objects::nonNull)
				.map(AddressModel::getZipCode)
				.filter(StringUtils::isNotBlank).filter(z -> !ZIP_4.equals(z))
				.collect(Collectors.toCollection(HashSet::new));

		assertThat(result, Matchers.not(Matchers.empty()));
		assertThat(result, Matchers.hasItems(ZIP_1, ZIP_2, ZIP_3, ZIP_5));
	}

	@Test
	public void testGetZipCodesInAMap()
	{
		List<AbstractOrderModel> orders = createOrders();
//		Map<String, List<String>> result = new HashMap<>();
//		for (AbstractOrderModel order : orders)
//		{
//			List<String> values = new ArrayList<>();
//			for (AbstractOrderEntryModel entry : order.getEntries())
//			{
//				AddressModel addressModel = entry.getDeliveryAddress();
//				String zipCode = addressModel.getZipCode();
//				if (StringUtils.isNotBlank(zipCode))
//				{
//					values.add(zipCode);
//				}
//			}
//			result.put(order.getCode(), values);
//		}
		Map<String, List<String>> result = orders.stream().map(this::getZipCodes)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));

		assertThat(result.keySet(), Matchers.not(Matchers.empty()));
		assertThat(result.keySet(), Matchers.hasItems(ORDER_1, ORDER_2));
		assertThat(result.get(ORDER_1), Matchers.hasItems(ZIP_1, ZIP_2));
		assertThat(result.get(ORDER_2), Matchers.hasItems(ZIP_3, ZIP_4, ZIP_5));
	}

	private Pair<String, List<String>> getZipCodes(AbstractOrderModel order){
		List<String> zipCodes = order.getEntries().stream()
				.map(AbstractOrderEntryModel::getDeliveryAddress)
				.map(AddressModel::getZipCode)
				.filter(StringUtils::isNotBlank).collect(Collectors.toList());
		return Pair.of(order.getCode(), zipCodes);
	}

	private List<String> createList()
	{
		return Arrays.asList(ONE, TWO, THREE, FOUR, FIVE);
	}

	private List<AbstractOrderModel> createOrders()
	{
		List<AbstractOrderModel> orders = new ArrayList<>();
		orders.add(
				createOrder(ORDER_1, () -> createAddress(ZIP_1), () -> createAddress(StringUtils.EMPTY), () -> createAddress(ZIP_2)));
		orders.add(createOrder(ORDER_2, () -> createAddress(ZIP_3), () -> createAddress(ZIP_4), () -> createAddress(ZIP_5),
				() -> createAddress(StringUtils.EMPTY)));
		return orders;
	}

	private AbstractOrderModel createOrder(String code, Supplier<AddressModel>... addressSuppliers)
	{
		AbstractOrderModel order = new AbstractOrderModel();
		order.setCode(code);
		List<AbstractOrderEntryModel> entryModels = new ArrayList<>();
		order.setEntries(entryModels);
		for (Supplier<AddressModel> supplier : addressSuppliers)
		{
			AbstractOrderEntryModel entry = new AbstractOrderEntryModel();
			entry.setDeliveryAddress(supplier.get());
			entryModels.add(entry);
		}
		return order;
	}

	private AddressModel createAddress(String zipCode)
	{
		AddressModel addressModel = new AddressModel();
		addressModel.setZipCode(zipCode);
		return addressModel;
	}
}
