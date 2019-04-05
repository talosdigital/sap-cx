package com.talos.meetup.lambdaMethodReferences;

import com.talos.meetup.model.ProductModel;
import java.util.function.Function;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Test4ClassInstanceMethod
{

	public static final String CODE_1 = "3454567";
	public static final String CODE_2 = "5676743";

	private void callAsserts(Function<ProductModel, String> getter)
	{
		ProductModel pant = new ProductModel();
		pant.setCode(CODE_1);
		ProductModel shirt = new ProductModel();
		shirt.setCode(CODE_2);

		assertEquals(CODE_1, getter.apply(pant));
		assertEquals(CODE_2, getter.apply(shirt));
	}

	@Test
	public void testAnonymous()
	{
		Function<ProductModel, String> getter = new Function<ProductModel, String>()
		{
			@Override
			public String apply(ProductModel productModel)
			{
				return productModel.getCode();
			}
		};

		callAsserts(getter);
	}

	@Test
	public void testLambda()
	{
		Function<ProductModel, String> getter = productModel -> productModel.getCode();

		callAsserts(getter);
	}

	@Test
	public void testMR()
	{
		Function<ProductModel, String> getter = ProductModel::getCode;

		callAsserts(getter);
	}

}
