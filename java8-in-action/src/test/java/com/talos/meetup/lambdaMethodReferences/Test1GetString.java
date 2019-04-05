package com.talos.meetup.lambdaMethodReferences;

import java.util.function.Function;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Test1GetString
{
	private static final String MESSAGE = "the message %d";

	private void callAsserts(Function<Integer, String> generator)
	{
		assertEquals("the message 1", generator.apply(1));
		assertEquals("the message 2", generator.apply(2));
		assertEquals("the message 3", generator.apply(3));
	}

	@Test
	public void testAnonymous()
	{
		Function<Integer, String> generator = new Function<Integer, String>()
		{
			@Override
			public String apply(Integer integer)
			{
				return String.format(MESSAGE, integer);
			}
		};

		callAsserts(generator);
	}

	@Test
	public void testLambda()
	{
		Function<Integer, String> generator = integer -> String.format(MESSAGE, integer);


		callAsserts(generator);
	}

	@Test
	public void testMR()
	{
		Function<Integer, String> generator = this::convert;


		callAsserts(generator);
	}

	private String convert(Integer integer){
		return String.format(MESSAGE, integer);
	}
}
