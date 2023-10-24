package com.ahannon.views;

import com.ahannon.annotations.Route;
import com.ahannon.request.HTTPRequest;
import com.ahannon.response.HTTPResponse;

@Route(route = "/")
public class TestView extends View {
	@Override
	public HTTPResponse GET(HTTPRequest request) {
		return new HTTPResponse(200);
	}

	@Override
	public HTTPResponse POST(HTTPRequest request) {
		return new HTTPResponse(200);
	}
}
