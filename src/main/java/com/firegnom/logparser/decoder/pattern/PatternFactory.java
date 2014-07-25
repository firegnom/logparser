package com.firegnom.logparser.decoder.pattern;

import java.util.HashMap;

class PatternFactory {

	static final private  HashMap<String, Pattern<?>> patternMap;

	static {
		patternMap = new HashMap<String, Pattern<?>>();
		addPattern(new UnixTimestampPattern());
		addPattern(new StringPattern());
	}

	static Pattern<?> create(String string) {
		return patternMap.get(string);
	}

	static private void addPattern(Pattern<?> p) {
		patternMap.put(p.getPattern(), p);
	}

}
