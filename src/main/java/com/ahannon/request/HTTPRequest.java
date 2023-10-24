package com.ahannon.request;

import java.util.Hashtable;

import org.json.JSONObject;

/**
 * An object representing HTTP requests
 */
public class HTTPRequest {
	private String httpMethod;
	private String route;
	private String httpVersion;
	private String requestLine;
	private Hashtable<String, String> headers;
	private JSONObject data;

	public HTTPRequest(String requestLine, Hashtable<String, String> headers, JSONObject data) {
		this.requestLine = requestLine;
		this.headers = headers;
		this.data = data;

		/* Set http method, route, and http version from request line */
		String[] splits = requestLine.split(" ");
		this.httpMethod = splits[0];
		this.route = splits[1];
		this.httpVersion = splits[2];
	}

	/**
	 * Get the route of the request
	 * 
	 * @return the route
	 */
	public String getRoute() {
		return this.route;
	}

	/**
	 * Get the http method of the request
	 * 
	 * @return the route
	 */
	public String getHTTPMethod() {
		return this.httpMethod;
	}

	/**
	 * Get the http version of the request
	 * 
	 * @return the route
	 */
	public String getHTTPVersion() {
		return this.httpVersion;
	}

	/**
	 * Get the request line of the request
	 * 
	 * @return the request line
	 */
	public String getRequestLine() {
		return this.requestLine;
	}

	/**
	 * Get the value of a header given a headerKey representing the key of a header
	 * 
	 * @param headerKey The key of the header
	 * @return the header value
	 */
	public String getHeader(String headerKey) {
		return this.headers.get(headerKey);
	}

	/**
	 * Get the body of the request
	 * 
	 * @return the body of the request
	 */
	public JSONObject getData() {
		return this.data;
	}

	@Override
	/**
	 * Turn this request into a string representation
	 * 
	 * @return string representation of this request
	 */
	public String toString() {
		StringBuilder string = new StringBuilder();
		/* Append requestLine */
		string.append(this.requestLine);
		string.append("\r\n");

		/* Append headers */
		this.headers.forEach((key, value) -> {
			string.append(String.format("%s: %s", key, value));
			string.append("\r\n");
		});
		string.append("\r\n");

		/* Append body */
		string.append(this.data);
		string.append("\r\n");

		/* Return string */
		return string.toString();
	}
}
