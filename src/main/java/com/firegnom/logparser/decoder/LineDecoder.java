package com.firegnom.logparser.decoder;

import java.nio.CharBuffer;

/**
 * The LineDecoder is responsible for decoding a buffer containing a line to
 * object .
 */
public interface LineDecoder {

	/**
	 * decode function is responsible for decoding char buffer and creatin
	 * object out of it
	 *
	 * @param buffer
	 *            the buffer
	 * @return the object[]
	 */
	public Object[] decode(CharBuffer buffer);

	/**
	 * this function returns Class of field passed as a parameter
	 *
	 * @param field
	 *            the field
	 * @return the class<?>
	 */
	public Class<?> fieldType(int field);
}
