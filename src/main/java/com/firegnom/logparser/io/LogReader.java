package com.firegnom.logparser.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import com.firegnom.logparser.thread.LineParser;
import com.firegnom.logparser.thread.LineParserPool;

/**
 * The Class LogReader is responsible for reading file and passing line by line
 * to {@link LineParserPool}.
 */
public class LogReader {

	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 1024 * 4;

	/** The Constant EOF. */
	private static final int EOF = -1;

	/** The inbuf is the buffer used to read data from the file. */
	private byte[] inbuf;

	/** The pool. */
	private LineParserPool pool;

	/**
	 * The Constructor.
	 *
	 * @param pool
	 *            the pool
	 */
	public LogReader(final LineParserPool pool) {
		this.pool = pool;
		inbuf = new byte[BUFFER_SIZE];
	}

	/**
	 * Read lines from the file passed as a prameter and pass them to parsing
	 * pool
	 *
	 * @param reader
	 *            the reader
	 * @param follow
	 *            the follow
	 * @throws IOException
	 *             the IO exception
	 */
	public void read(final RandomAccessFile reader, boolean follow)
			throws IOException {
		LineParser parser = pool.getParser();
		int num;
		while ((num = reader.read(inbuf)) >= 0 || follow) {
			for (int i = 0; i < num; i++) {
				final byte ch = inbuf[i];
				switch (ch) {
				case '\r':
				case '\n':
					pool.execute(parser);
					parser = pool.getParser();
					break;
				default:
					parser.getBuffer().put(ch);
				}
			}
		}

	}

}
