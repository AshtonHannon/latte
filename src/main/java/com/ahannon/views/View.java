package com.ahannon.views;

import com.ahannon.request.HTTPRequest;
import com.ahannon.response.HTTPResponse;

public abstract class View {
	public HTTPResponse GET(HTTPRequest request) {
		return new HTTPResponse(405);
	}

	public HTTPResponse POST(HTTPRequest request) {
		return new HTTPResponse(405);
	}

}