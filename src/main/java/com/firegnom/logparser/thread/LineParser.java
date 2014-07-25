package com.firegnom.logparser.thread;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

import com.firegnom.logparser.decoder.LineDecoder;
import com.firegnom.logparser.decoder.LineDecoderListener;

public class LineParser implements Runnable{
	
	private static final int BUFFER_SIZE = 1024;
	
	private final CharsetDecoder cDecoder;
	private final ByteBuffer bBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	private final CharBuffer cBuffer = CharBuffer.allocate(BUFFER_SIZE);
	
	private final LineDecoder lineDecoder;
	
	private final LineDecoderListener lineLisrener;
	
	private ParserFinishNotifier listener;
	
	
	LineParser(Charset cSet, ParserFinishNotifier listener,LineDecoder lineDecoder,  LineDecoderListener lineLisrener) {
		this.listener = listener;
		this.lineDecoder = lineDecoder;
		this.lineLisrener = lineLisrener;
		cDecoder = cSet.newDecoder();
	}
	
	synchronized public ByteBuffer getBuffer(){
		return bBuffer;
	}
	
	public void run() {
		bBuffer.flip();
		cBuffer.clear();
		cDecoder.reset();
		cDecoder.decode(bBuffer, cBuffer, false);
		cBuffer.flip();
		lineLisrener.decoded(lineDecoder.decode(cBuffer));
		bBuffer.clear();
		listener.finished(this);
	}

	public void stop() {
		//release reference to the pool
		listener = null;
	}

	public void clear() {
		bBuffer.clear();
		cBuffer.clear();
	}

}
