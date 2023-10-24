package com.ahannon.request;

import java.util.Hashtable;

import org.json.JSONObject;

import com.ahannon.exceptions.HTTPRequestFormatException;

/**
 * A utility to help build an HTTPRequest object
 */
public class HTTPRequestBuilder {
	private String requestLine;
	private Hashtable<String, String> headers;
	private StringBuffer data;

	public HTTPRequestBuilder() {
		this.headers = new Hashtable<String, String>();
		this.data = new StringBuffer();
	}

	/**
	 * Get the currently set request line
	 * 
	 * @return the request line
	 */
	public String getRequestLine() {
		return this.requestLine;
	}

	/**
	 * Set the request line
	 * 
	 * @param requestLine
	 * @throws HTTPRequestFormatException
	 */
	public void setRequestLine(String requestLine) throws HTTPRequestFormatException {
		if (requestLine == null || requestLine.length() == 0) {
			throw new HTTPRequestFormatException("Attempted to set an invalid request line");
		}
		this.requestLine = requestLine;
	}

	/**
	 * Get the currently set header
	 * 
	 * @param headerKey The key of the requested header
	 * @return the header associated with the given headerKey
	 */
	public String getHeader(String headerKey) {
		return this.headers.get(headerKey);
	}

	/**
	 * Given a string of a header containing the header key and value, add the
	 * header to the header Hashtable
	 * 
	 * @param headerLine The line of the header from the request
	 * @throws HTTPRequestFormatException
	 */
	public void appendHeaderLine(String headerLine) throws HTTPRequestFormatException {
		int i = headerLine.indexOf(":");
		if (i == -1) {
			throw new HTTPRequestFormatException("Attempted to add an invalid header");
		}
		this.headers.put(headerLine.substring(0, i), headerLine.substring(i + 2, headerLine.length()));
	}

	/**
	 * Get the currently body
	 * 
	 * @return the body of the request
	 */
	public String getData() {
		return this.data.toString();
	}

	/**
	 * Given a string of the entire body for the request, set the body
	 * 
	 * @param bodyLine The data of the body from the request
	 */
	public void appendData(String bodyLine) {
		this.data.append(bodyLine).append("\r\n");
	}

	/**
	 * Return the currently build HTTPRequest as an HTTPRequest object
	 * 
	 * @return the HTTPRequest object
	 */
	public HTTPRequest toHTTPRequest() {
		if (this.data.length() == 0) {
			return new HTTPRequest(this.requestLine, this.headers, null);
		} else {
			return new HTTPRequest(this.requestLine, this.headers, new JSONObject(this.data.toString()));
		}
	}
}
