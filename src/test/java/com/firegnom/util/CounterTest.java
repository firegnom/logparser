package com.firegnom.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class CounterTest {

	@Test
	public void test() {
		Counter p = new Counter(10, 5);
		
		for (int i = 0 ; i < 10;i++){
			p.count("a");
		}
		for (int i = 0 ; i < 9;i++){
			p.count("b");
		}
		for (int i = 0 ; i < 8; i++){
			p.count("a"+i);
		}
		
		CountElement count = p.getCount();
		
		assertEquals("a", count.getKey());
		assertEquals(10, count.getCount());
		assertEquals(5, p.size());
		
	}


}
