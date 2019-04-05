package com.talos.meetup.lambdaMethodReferences;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


/**
 * Log levels:
 * <p>
 * TRACE
 * DEBUG
 * -->  INFO  	<----
 * WARN
 * ERROR
 * FATAL
 */
public class Test5LogExample
{
	private static final Logger LOGGER = LogManager.getLogger(Test5LogExample.class);

	@Test
	public void testDebugLog()
	{
//		if (LOGGER.isDebugEnabled())
//		{
//			LOGGER.debug(this.computeMessage());
//		}
		LOGGER.debug(this::computeMessage);
	}

	@Test
	public void testInfoLog()
	{
//		if (LOGGER.isInfoEnabled())
//		{
//			LOGGER.info(this.computeMessage());
//		}
		LOGGER.info(this::computeMessage);
	}

	/**
	 * Creates a message. It is a CPU consuming process!
	 *
	 * @return
	 */
	private String computeMessage()
	{
		try
		{
			Thread.sleep(1000L);
		}
		catch (InterruptedException ex)
		{

		}
		return "Computed message";
	}
}
