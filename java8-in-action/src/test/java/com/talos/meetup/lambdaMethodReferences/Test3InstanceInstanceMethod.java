package com.talos.meetup.lambdaMethodReferences;

import com.talos.meetup.model.ProductModel;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Test3InstanceInstanceMethod
{

	private static final String CODE = "12231231";
	private ProductModel productModel;

	@Before
	public void init()
	{
		productModel = new ProductModel();
		productModel.setCode(CODE);
	}

	private void callAsserts(Supplier<String> getter)
	{
		String actual = getter.get();

		assertEquals(CODE, actual);
	}

	@Test
	public void testAnonymous()
	{

		Supplier<String> getter = new Supplier<String>()
		{
			@Override
			public String get()
			{
				return productModel.getCode();
			}
		};

		callAsserts(getter);
	}

	@Test
	public void testLambda()
	{

		Supplier<String> getter = () -> productModel.getCode();

		callAsserts(getter);
	}

	@Test
	public void testMR()
	{

		Supplier<String> getter =  productModel::getCode;

		callAsserts(getter);
	}
}
