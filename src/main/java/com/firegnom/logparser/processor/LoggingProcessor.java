package com.firegnom.logparser.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.logparser.decoder.LineDecoder;

public class LoggingProcessor implements Processor{
	final Logger logout ;
	final private LoggingStrategy strategy;
	
	public LoggingProcessor(final String loggerName,final LoggingStrategy strategy) {
		this.strategy = strategy;
		logout = LoggerFactory.getLogger(loggerName);
	}

	@Override
	synchronized public void process(final Object[] data, final LineDecoder decoder) {
		if (strategy.filter(data, decoder) ){
			//TODO this part is hardcoded and should be configurable
			logout.info("Connection made at "+data[0]+" from " +data[1] + " to "+data[2]);
		}
		
	}

	@Override
	public void flush() {
		// nothing to do logs are flushed on the go
	}
	
}
