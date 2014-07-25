package com.firegnom.logparser.decoder.pattern;

import java.nio.CharBuffer;

public interface Pattern <T>{
	String getPattern();
	Class<T> getType();
	T decode(CharBuffer buffer,int start, int stop);
}
