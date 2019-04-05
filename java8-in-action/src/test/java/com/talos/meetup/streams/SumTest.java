package com.talos.meetup.streams;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class SumTest
{

	private static final Path PATH = Paths.get("/Users/mvalderrama/Downloads/workspace/meet1/src/main/resources/data.txt");
	private static final BigDecimal EXPECTED_VALUE = new BigDecimal("3999515688321864.11");

	private List<String> lines;
	private Stream<String> stream;

	@Before
	public void initStream() throws Exception
	{
		lines = Files.readAllLines(PATH);
		stream = Files.lines(PATH);
	}


	@Test
	public void testSequentialFor() throws Exception
	{
		BigDecimal result = BigDecimal.ZERO;
		for (String line : lines)
		{
			result = result.add(new BigDecimal(line));
		}
		assertEquals(EXPECTED_VALUE, result);
	}

	@Test
	public void testLambda() throws Exception
	{
		BigDecimal result = stream.map(BigDecimal::new).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

		assertEquals(EXPECTED_VALUE, result);
	}

	@Test
	public void testParallel() throws Exception
	{
		BigDecimal result = stream.parallel().map(BigDecimal::new).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

		assertEquals(EXPECTED_VALUE, result);
	}
}
