package com.firegnom.logparser.processor;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.logparser.decoder.LineDecoder;
import com.firegnom.util.CountElement;
import com.firegnom.util.Counter;

/**
 * <p>
 * The Class CountProcessor is responsible for counting number of times given
 * hosts are contacted.
 * </p>
 * <p>
 * Data is printed to logfile only if there is a data beeing processed
 * </p>
 */
public class CountProcessor implements Processor {
	private final Logger logout = LoggerFactory
			.getLogger("CountProcessorOutput");

	/**
	 * The Constant COUNTER_LIMIT limits counter size counter reduce size is
	 * half of this size.
	 */
	private static final int COUNTER_LIMIT = 100000;

	/** The fields. to be added to the counter */
	// TODO Hardcoded values
	int[] fields = { 1, 2 };

	/** The counter. */
	private Counter counter;

	private long lastClear;
	private final long clearPeriod = TimeUnit.HOURS.toMillis(1);

	/**
	 * The Constructor.
	 */
	public CountProcessor() {
		counter = new Counter(COUNTER_LIMIT, COUNTER_LIMIT / 2);
		lastClear = System.currentTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.logparser.processor.Processor#handleData(java.lang.Object[])
	 */
	@Override
	synchronized public void process(final Object[] d, final LineDecoder decoder) {
		checkTime();
		for (int i = 0; i < fields.length; i++) {
			getCounter().count((String) d[fields[i]]);
		}
	}

	/**
	 * Check time is executed every time data is processed to check if we need
	 * to print out statistics. it uses Systems current time to do the check.
	 *
	 */
	public void checkTime() {
		checkTime(System.currentTimeMillis());
	}

	private void logData() {
		CountElement count = getCounter().getCount();
		if (count == null)
			return;
		logout.info("Host making most connections : " + count.getKey()
				+ " , connections done : " + count.getCount());
	}

	/**
	 * Check time is executed every time data is processed to check if we need
	 * to print out statistics.
	 *
	 * @param now
	 *            the time to be checked
	 */
	public void checkTime(long now) {
		if (clearPeriod < (now - lastClear)) {
			lastClear = System.currentTimeMillis();
			logData();
			getCounter().clear();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.logparser.processor.Processor#flush()
	 */
	@Override
	public void flush() {
		logData();

	}

	public Counter getCounter() {
		return counter;
	}

}
