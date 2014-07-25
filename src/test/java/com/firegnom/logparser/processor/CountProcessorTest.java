package com.firegnom.logparser.processor;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountProcessorTest {

	@Test
	public void test() {
		CountProcessor cp = new CountProcessor();
		cp.checkTime(System.currentTimeMillis()+(1000*60*60*2));
	}

}
