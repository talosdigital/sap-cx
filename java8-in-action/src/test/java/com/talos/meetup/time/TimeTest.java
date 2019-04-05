package com.talos.meetup.time;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.function.IntSupplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;


@RunWith(ConcurrentTestRunner.class)
public class TimeTest
{

	private static final String DATE = "2018-07-23";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

	@Test
	public void testParsing() throws Exception
	{
		Date date = formatter.parse(DATE);

		check_2018_07_23_Date(() -> date.getYear() + 1900, () -> date.getMonth() + 1, date::getDate);
	}

	@Test
	public void testParsing2() throws Exception
	{
		LocalDate localDate = LocalDate.from(FORMATTER.parse(DATE));

		check_2018_07_23_Date( localDate::getYear, localDate.getMonth()::getValue, localDate::getDayOfMonth);
	}

	@Test
	public void testAdding()
	{
		int yearDifference = 1;
		int monthDifference = 3;
		int dayDifference = 20;
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018 - yearDifference, 7 - 1 - monthDifference, 23 - dayDifference);

		calendar.add(Calendar.YEAR, yearDifference);
		calendar.add(Calendar.MONTH, monthDifference);
		calendar.add(Calendar.DAY_OF_MONTH, dayDifference);

		check_2018_07_23_Date(() -> calendar.get(Calendar.YEAR), () -> calendar.get(Calendar.MONTH) + 1,
				() -> calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testAdding2()
	{
		int yearDifference = 1;
		int monthDifference = 3;
		int dayDifference = 20;
		LocalDate localDate = LocalDate.of(2018 - yearDifference, 7 - monthDifference, 23 - dayDifference);

		localDate = localDate.plusYears(yearDifference).plusMonths(monthDifference).plusDays(dayDifference);

		check_2018_07_23_Date( localDate::getYear, localDate.getMonth()::getValue, localDate::getDayOfMonth);
	}

	/**
	 * Checks if the given year is 2018, the month is 7 and the day of the month is 23
	 *
	 * @param getYear
	 * @param getMonth
	 * @param getDay
	 */
	private void check_2018_07_23_Date(IntSupplier getYear, IntSupplier getMonth, IntSupplier getDay)
	{
		assertEquals(2018, getYear.getAsInt());
		assertEquals(7, getMonth.getAsInt());
		assertEquals(23, getDay.getAsInt());
	}
}
