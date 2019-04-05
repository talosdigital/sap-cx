package com.talos.meetup.lambdaMethodReferences;

import com.talos.meetup.lambdaMethodReferences.data.FutureStockData;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Date.from;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class Test6FutureStock
{

	private void checkAsserts(Comparator<FutureStockData> sorter)
	{
		FutureStockData fsd1 = create("2017-07-30");
		FutureStockData fsd2 = create("2018-03-12");
		FutureStockData fsd3 = create("2018-08-22");
		FutureStockData fsd4 = create("2019-03-01");
		FutureStockData fsd5 = create("2019-03-02");
		FutureStockData fsd6 = create(null);
		List<FutureStockData> result = asList(fsd5, fsd3, fsd4, fsd6, fsd1, fsd2);

		result.sort(sorter);

		Assert.assertThat(result, Matchers.hasItems(fsd1, fsd2, fsd3, fsd4, fsd5, fsd6));
	}

	@Test
	public void testAnonymous()
	{
		Comparator<FutureStockData> sorter = new Comparator<FutureStockData>()
		{
			@Override
			public int compare(FutureStockData o1, FutureStockData o2)
			{
				Date date1 = o1.getDate();
				Date date2 = o2.getDate();
				if (isNull(date1) && isNull(date2))
				{
					return 0;
				}
				else if (isNull(date1))
				{
					return -1;
				}
				else if (isNull(date2))
				{
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		checkAsserts(sorter);
	}

	@Test
	public void testLambda()
	{
		Comparator<FutureStockData> sorter = comparing(f -> f.getDate(), Comparator.nullsLast((d1, d2) -> d1.compareTo(d2)));

		checkAsserts(sorter);
	}


	@Test
	public void testMR()
	{
		Comparator<FutureStockData> sorter = comparing(FutureStockData::getDate, Comparator.nullsLast(Date::compareTo));

		checkAsserts(sorter);
	}


	private FutureStockData create(String str)
	{
		FutureStockData futureStockData = new FutureStockData();
		if (nonNull(str))
		{
			Date date = from(LocalDate.from(ISO_DATE.parse(str)).atStartOfDay(systemDefault()).toInstant());
			futureStockData.setDate(date);
		}
		return futureStockData;
	}
}
