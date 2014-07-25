package com.firegnom.logparser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URL;

import org.junit.Test;

import com.firegnom.logparser.processor.CountProcessor;
import com.firegnom.logparser.processor.Processor;
import com.firegnom.logparser.processor.SimpleHardcodedProcessor;
import com.firegnom.util.CountElement;

public class LogParserTest {

	@Test
	public void test() throws Exception {
		
		URL resource = getClass().getResource("/largeData.txt");
		RandomAccessFile file = new RandomAccessFile(resource.getPath(), "r");
		LogParser logParser = new LogParser(file , false,"z","a");
		logParser.parse();
		
		SimpleHardcodedProcessor processor = (SimpleHardcodedProcessor) logParser.getProcessor();
		
		CountProcessor p = (CountProcessor) processor.getProcessors().get(2);
		
		CountElement count = p.getCounter().getCount();
		assertEquals("z", count.getKey());
		assertEquals(235, count.getCount());
		
		
	}

}
