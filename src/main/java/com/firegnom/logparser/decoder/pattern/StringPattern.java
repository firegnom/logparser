package com.firegnom.logparser.decoder.pattern;

import java.nio.CharBuffer;

public class StringPattern implements Pattern<String>{
	
	

	private static final String PATTERN = "s";

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String decode(final CharBuffer buffer, final int start,final int stop) {
		buffer.position(start);
		String string = buffer.subSequence(0, stop - start).toString();
		buffer.position(stop);
		return string;
	}

	@Override
	public String getPattern() {
		return PATTERN;
	}

}
