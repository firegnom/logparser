package com.firegnom.logparser.decoder;

/**
 * The Interface LineDecoderListener is used to listen for Line decoder to
 * decode a line and pass it as a parameter of function decode
 */
public interface LineDecoderListener {

	/**
	 * function Decoded is executed when data in the buffer is decoded and
	 * converted to object
	 *
	 * @param line
	 *            the line
	 */
	void decoded(Object[] line);
}
