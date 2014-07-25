package com.firegnom.prototype;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tail {

	static long sleepTime = 1000;

	public static void main(String[] args) throws IOException {

			if (args.length > 1)
				sleepTime = Long.parseLong(args[1]) * 1000;

			BufferedReader input = new BufferedReader(new FileReader(
					"c:/dev/text.txt"));
			String currentLine = null;

			while (true) {

				if ((currentLine = input.readLine()) != null) {
					System.out.println(currentLine);
					continue;
				}

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}

			}
			input.close();

	}
}
