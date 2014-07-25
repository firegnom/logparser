package com.firegnom.logparser.thread;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

import com.firegnom.logparser.decoder.LineDecoder;
import com.firegnom.logparser.decoder.LineDecoderListener;

public class LineParserPoolTest {

	@Test
	public void test() throws Exception {
		LineParserPool lpp = new LineParserPool(new LineDecoder() {
			@Override
			public Object[] decode(CharBuffer buffer) {
				System.out.println("Buffer["+ buffer+"]");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					fail();
				}
				return null;
			}

			@Override
			public Class<?> fieldType(int field) {
				return null;
			}
		},new LineDecoderListener() {
			@Override
			public void decoded(Object[] line) {
				
			}
		},Charset.forName("UTF-8"));
		
		
		for (int i = 0 ; i < 200 ; i ++){
			System.out.println("i = "+i);
			LineParser parser = lpp.getParser();
			ByteBuffer buffer = parser.getBuffer();
			CharsetEncoder ce = Charset.forName("UTF-8").newEncoder();
			buffer.put(ce.encode(CharBuffer.wrap("test\n".toCharArray())));
			buffer.flip();
			System.out.println(buffer);
			lpp.execute(parser);
		}
		
		
	}

}
