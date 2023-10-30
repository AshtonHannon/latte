package com.ahannon.response;

import org.json.JSONObject;

public class HTTPResponse {
	private Integer status;
	private JSONObject data = null;

	public HTTPResponse(Integer status) {
		this.status = status;
	}

	public HTTPResponse(Integer status, JSONObject data) {
		this.status = status;
		this.data = data;
	}

	public Integer getStatus() {
		return this.status;
	}

	public JSONObject getData() {
		return this.data;
	}

	public String getResponse() {
		/* New string builder to build response */
		StringBuilder response = new StringBuilder();

		/* Response Line */
		Integer statusCode = this.getStatus();
		String statusMessage = ResponseCodes.CODES.get(statusCode);
		response.append(String.format("HTTP/1.1 %d %s\r\n", statusCode, statusMessage));

		/* Data */
		if (data != null && data instanceof JSONObject) {
			/* Append content type as 'application/json' */
			response.append("Content-Type: application/json\r\n");
			
			/* Get and append the content length */
			Integer contentLength = this.data.toString().getBytes().length;
			response.append(String.format("Content-Length: " + contentLength + "\r\n\r\n"));

			/* Append data */
			response.append(this.data.toString());
		} else {
			/* Otherwise just append new line */
			response.append("\r\n");
		}

		/* Return response as string */
		return response.toString();
	}

}
