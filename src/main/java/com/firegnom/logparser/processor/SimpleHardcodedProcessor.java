package com.firegnom.logparser.processor;

import java.util.ArrayList;
import java.util.List;

import com.firegnom.logparser.decoder.LineDecoder;


//TODO
/**
 * Unfortunately there is not enough time to create some nice configuration for
 * this processor with some nice DSL for easy configuration and flexibility for
 * example reading properties like that :
 * 
 * <pre>
 * processors:	
 * 	- CountProcesor(2,3)
 * 	- LogProcessor(2 = abcd)
 * 	- LogProcessor(3 = abcd)
 * </pre>
 * 
 * so those three processors have been hardcoded to 
 * 
 */
public class SimpleHardcodedProcessor implements Processor{
	private List<Processor> processors  = new ArrayList<Processor>();
	
	public SimpleHardcodedProcessor(final String hostFrom , final String hostTo) {
		// create logging processor which will log out all connections from certain host
		getProcessors().add(new LoggingProcessor("ConnectionsFrom", new LoggingStrategy() {
			@Override
			public boolean filter(Object[] data, LineDecoder decoder) {
				return ((String)data[1]).equalsIgnoreCase(hostFrom);
			}
		}));
		//create processor which will 
		getProcessors().add(new LoggingProcessor("ConnectionsTo", new LoggingStrategy() {
			@Override
			public boolean filter(Object[] data, LineDecoder decoder) {
				return ((String)data[2]).equalsIgnoreCase(hostTo);
			}
		}));
		
		//create counting processor and add it in to the list of processors
		getProcessors().add(new CountProcessor());
		
	
	}

	@Override
	synchronized public void process(Object[] d, LineDecoder decoder) {
		for (Processor p : getProcessors()){
			p.process(d, decoder);
		}
		
	}

	@Override
	public void flush() {
		for (Processor p : getProcessors()){
			p.flush();
		}
		
	}

	public List<Processor> getProcessors() {
		return processors;
	}

}
