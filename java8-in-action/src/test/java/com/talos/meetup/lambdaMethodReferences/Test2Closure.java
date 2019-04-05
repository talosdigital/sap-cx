package com.talos.meetup.lambdaMethodReferences;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Assert;
import org.junit.Test;


/**
 * A closure is a special kind of object that combines two things: a function, and the environment in which that function was created.
 * The environment consists of any local variables that were in-scope at the time that the closure was created
 */
public class Test2Closure
{

	public static final int SIZE = 20;

	private void callAsserts(IntSupplier intSupplier)
	{
		for (int i = 0; i < SIZE; i++)
		{
			int actual = intSupplier.getAsInt();
			Assert.assertEquals(i, actual);
		}
	}

	@Test
	public void testAnonymous() throws Exception
	{
		AtomicInteger sequence = new AtomicInteger(0);
		IntSupplier intSupplier = new IntSupplier()
		{
			@Override
			public int getAsInt()
			{
				return sequence.getAndIncrement();
			}
		};

		callAsserts(intSupplier);
	}

	@Test
	public void testLambda() throws Exception
	{
		AtomicInteger sequence = new AtomicInteger(0);
		IntSupplier intSupplier = () -> sequence.getAndIncrement();

		callAsserts(intSupplier);
	}

	@Test
	public void testMR() throws Exception
	{
		AtomicInteger sequence = new AtomicInteger(0);
		IntSupplier intSupplier = sequence::getAndIncrement;

		callAsserts(intSupplier);
	}
}
