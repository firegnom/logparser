package com.firegnom.logparser.decoder.pattern;

import java.nio.CharBuffer;

public class UnixTimestampPattern implements Pattern<Long>{
	
	

	private static final String PATTERN = "ut";

	@Override
	public Class<Long> getType() {
		return Long.class;
	}

	@Override
	public Long decode(CharBuffer buffer, int start, int stop) {
		buffer.position(start);
		String string = buffer.subSequence(0, stop - start).toString(); 
		buffer.position(stop);
		return new Long(string.trim()) ;
	}

	@Override
	public String getPattern() {
		return PATTERN;
	}

}
