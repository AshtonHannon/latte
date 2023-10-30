package com.ahannon.views.rand.test1.test2;

import org.json.JSONObject;

import com.ahannon.annotations.Route;
import com.ahannon.request.HTTPRequest;
import com.ahannon.response.HTTPResponse;
import com.ahannon.views.View;

@Route(route = "nested/")
public class NestedRouteView extends View {
	@Override
	public HTTPResponse GET(HTTPRequest request) {
		return new HTTPResponse(200, new JSONObject("{'test': 'success'}"));
	}
}
