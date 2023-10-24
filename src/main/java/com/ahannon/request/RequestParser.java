package com.ahannon.request;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

	/**
	 * Given the input stream, read the appropriate amount of data in order to order
	 * to turn the request into a string
	 * 
	 * @param stream InputSteam of the incoming request
	 * @return String on success, otherwise null
	 */
	private static String buildRequestString(InputStream stream) {
		try {
			/* Create BufferedReader from InputStream */
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			/* Data StringBuilder to build the data string */
			StringBuilder data = new StringBuilder();
			/* Content-Length header... used to read body data of post requests */
			Integer contentLength = null;

			/* Read the request line and all of the header lines sent */
			String line = reader.readLine();
			while (line != null && line.length() > 0) {
				data.append(line);
				data.append("\r\n");
				line = reader.readLine();
			}

			/*
			 * Using RegEx, check to see if a value for 'Content-Length: ?' can be found.
			 * If one is found, then there is some data being sent with the request, so
			 * loop for that number of bytes, and add the char representation of those bytes
			 * to the data.
			 */
			Pattern pattern = Pattern.compile("Content-Length: (\\d+)");
			Matcher matcher = pattern.matcher(data.toString());
			if (matcher.find()) {
				/* Get the value of the content length */
				contentLength = Integer.parseInt(matcher.group(1));
				/* Append extra empty line for break between headers and data */
				data.append("\r\n");
				/* Loop and all all of the char bytes to the data */
				for (int i = 0; i < contentLength; i++) {
					data.append((char) reader.read());
				}
			}

			/* Return the data as a string */
			return data.toString();

		} catch (Exception e) {
			/* Exception caught. Print stackTrace and return null */
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Given the InputStream of the incoming reuqest, parse the data and return an
	 * HTTPRequest
	 * 
	 * @param stream
	 * @return the request object
	 */
	public static HTTPRequest parse(InputStream stream) {
		String requestString = buildRequestString(stream);
		BufferedReader reader = new BufferedReader(new StringReader(requestString));
		HTTPRequestBuilder httpRequest = new HTTPRequestBuilder();

		/* Set the request line */
		try {
			httpRequest.setRequestLine(reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Set the headers */
		try {
			String headerLine = reader.readLine();
			while (headerLine != null && headerLine.length() > 0) {
				httpRequest.appendHeaderLine(headerLine);
				headerLine = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		/* Set the body */
		try {
			String bodyLine = reader.readLine();
			while (bodyLine != null) {
				httpRequest.appendData(bodyLine);
				bodyLine = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Return HTTPRequest */
		return httpRequest.toHTTPRequest();
	}
}
