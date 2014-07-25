package com.firegnom.logparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.logparser.decoder.LineDecoder;
import com.firegnom.logparser.decoder.LineDecoderListener;
import com.firegnom.logparser.decoder.pattern.PatternLineDecoder;
import com.firegnom.logparser.io.LogReader;
import com.firegnom.logparser.processor.Processor;
import com.firegnom.logparser.processor.SimpleHardcodedProcessor;
import com.firegnom.logparser.thread.LineParserPool;

public class LogParser implements LineDecoderListener {
	private static final String CHARSET = "UTF-8";
	
	private final Logger log = LoggerFactory.getLogger(LogParser.class);
	

	private LogReader logReader;
	private LineDecoder lineDecoder;
	private LineParserPool pool;
	private Processor processor;
	private RandomAccessFile file;
	private boolean follow;

	public LogParser(RandomAccessFile file, final boolean follow ,String hostFrom,String hostTo ) {
		this.file = file;
		this.follow = follow;
		lineDecoder = new PatternLineDecoder("ut s s", ' ');
		pool = new LineParserPool(lineDecoder, this, Charset.forName(CHARSET));
		logReader = new LogReader(pool);
		processor = new SimpleHardcodedProcessor(hostFrom,hostTo);

	}

	public void parse() {
		try {
			logReader.read(file, follow);
		} catch (IOException e) {
			log.error("exception while parsing file",e);
		}
		processor.flush();
	}

	/**
	 * Decoded is executed when line is decoded and is executed in one of the
	 * pools threads
	 *
	 * @param line
	 *            the line
	 */
	@Override
	public void decoded(Object[] line) {
		// after data is decoded use processor to process this data this code is
		// executed on the time of one of the threads in the thread pool 
		processor.process(line, lineDecoder);
	}

	
	public Processor getProcessor(){
		return processor;
	}
	
	
	/**
	 * The main method.  takes as an imput 4 arguments
	 * <pre>
	 * LogParser filename follow hostFrom hostTo
	 * </pre>
	 * <ul>
	 * <li> filename - name of the file to process</li>
	 * <li> follow - boolean if follow is true LogParser will wait for data to be appended to file</li>
	 * <li> hostFrom - source host from which data will be logged </li>
	 * <li> hostTo - destination host from which data will be logged </li>
	 * </ul>
	 *
	 * @param args the args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 4 ){
			LogParser logParser = new LogParser(new RandomAccessFile(args[0], "r"), Boolean.getBoolean(args[1]), args[2], args[3]);
			logParser.parse();
		}else{
			printUsage();
		}
	}

	private static void printUsage() {
		System.out.println(" Usage : LogParser filename follow hostFrom hostTo\n See JavaDoc for more info");
	}
}
