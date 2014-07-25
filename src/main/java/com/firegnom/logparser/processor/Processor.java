package com.firegnom.logparser.processor;

import com.firegnom.logparser.decoder.LineDecoder;

/**
 * The Processor is responsible for working with line of data after it was
 * decoded by {@link LinedDecoder}.
 */
public interface Processor {

	/**
	 * Handle data function is executed when decoder decoded line from buffer
	 * and object is created , a decoder is passed as a parameter to help
	 * determine types of object array.
	 *
	 * @param d
	 *            the d
	 * @param decoder
	 *            the decoder
	 */
	void process(Object[] d, LineDecoder decoder);

	/**
	 * Flush all data .
	 */
	void flush();
}
