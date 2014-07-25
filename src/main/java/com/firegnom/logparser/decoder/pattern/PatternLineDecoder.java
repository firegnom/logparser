package com.firegnom.logparser.decoder.pattern;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import com.firegnom.logparser.decoder.LineDecoder;

public class PatternLineDecoder implements LineDecoder {

	private final String pattern;
	private final char separator; // = ' ';

	//TODO  this field should be loaded from properties 
	private static final String patternSeparator = " ";

	private final int lineLength;

	private final List<Pattern<?>> patterns;
	private char c;

	/**
	 * 
	 * */
	public PatternLineDecoder(final String pattern, final char separator) {
		/*
		 * This is executed only once during startup so it do not require high
		 * performance but it must be easy to understand
		 */

		this.pattern = pattern;
		this.separator = separator;

		String[] split = pattern.split(patternSeparator);

		lineLength = split.length;
		if (lineLength <= 0) {
			// this is error in pattern initialization application can't run
			// throw runtime Exception and stop.
		}

		patterns = new ArrayList<Pattern<?>>(lineLength);

		for (int i = 0; i < split.length; i++) {
			patterns.add(PatternFactory.create(split[i]));
		}

	}

	@Override
	synchronized public Object[] decode(final CharBuffer buffer) {

		int start = 0;
		int position = 0;
		int currentPattern = 0;

		Object[] ret = new Object[lineLength];

		while (buffer.hasRemaining() && currentPattern < lineLength) {
			c = buffer.get();
			// check for separator if not quoted
			if (c == '\n'  || !buffer.hasRemaining()
					|| c == separator) {

				position = buffer.position();

				// get pattern for current item
				Object decode = null;
				try {
					
				decode = patterns.get(currentPattern).decode(buffer,
						start,
						c == separator || c == '\n' ? position - 1 : position);
				}catch(Exception e){
					//TODO log error
					System.out.println("if "+(c == '\n' || !buffer.hasRemaining()
							|| c == separator)+"Error in char "+c+" position "+position+" hasremaining "+buffer.hasRemaining()+" "+buffer);
				}
				ret[currentPattern] = decode;
				start = position;

				// just in case set last known position if pattern had to change
				// buffer position and did not go to
				buffer.position(position);
				currentPattern++;
			}

		}
		return ret;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	public Class<?> fieldType(int field) {
		return patterns.get(field).getType();
	}

}
