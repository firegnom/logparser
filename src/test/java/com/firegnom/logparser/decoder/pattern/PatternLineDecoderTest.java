package com.firegnom.logparser.decoder.pattern;

import static org.junit.Assert.*;

import java.nio.CharBuffer;

import org.junit.Test;

public class PatternLineDecoderTest {

	@Test
	public void test() {
		String test = "test";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("test", decode[0]);
		
	}
	
	@Test
	public void test_nl() {
		String test = "test\n";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("test", decode[0]);
		
	}
	
	@Test
	public void test_spaces() {
		String test = "test  ";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("test", decode[0]);
		
	}
	
	@Test
	public void test2() {
		String test = "test test2";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("test", decode[0]);
		assertEquals("test2", decode[1]);
		
	}
	
	
	/**
	 * Do not accept empty line return null
	 * */
	@Test
	public void testEmpty() {
		String test = "";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals(null, decode[0]);
		
	}
	
	@Test
	public void testSeparatorOnly() {
		String test = " ";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("", decode[0]);
		
	}
	
	@Test
	public void testMore() {
		String test = "test test2 test3";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals("test", decode[0]);
		assertEquals(1, decode.length);
	}
	
	@Test
	public void testRandom() {
		String test = "1405771418937 sycTFQxpsonQlnSefIrOfZMpKxsKIcBqJDLTlKLnirItcFSWJnOdailyAMFbBIl snYESuANlNFLyKDElXKOaaSiyeCAFlbYcIZPUJuWwcDJrYm";
		CharBuffer b = CharBuffer.wrap(test.toCharArray());
		
		PatternLineDecoder pd = new PatternLineDecoder("ut s s", ' ');
		Object[] decode = pd.decode(b);
		
		assertEquals(1405771418937l, decode[0]);
		assertEquals("sycTFQxpsonQlnSefIrOfZMpKxsKIcBqJDLTlKLnirItcFSWJnOdailyAMFbBIl", decode[1]);
		assertEquals("snYESuANlNFLyKDElXKOaaSiyeCAFlbYcIZPUJuWwcDJrYm", decode[2]);
	}
	
	
	
	
}
