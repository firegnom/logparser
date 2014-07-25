package com.firegnom.logparser.processor;

import com.firegnom.logparser.decoder.LineDecoder;

public interface LoggingStrategy {
	boolean filter(Object[] data, LineDecoder decoder);
}
