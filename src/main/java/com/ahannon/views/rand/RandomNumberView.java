package com.ahannon.views.rand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.ahannon.annotations.Route;
import com.ahannon.request.HTTPRequest;
import com.ahannon.response.HTTPResponse;
import com.ahannon.views.View;

@Route(route = "number/")
public class RandomNumberView extends View {

	@Override
	public HTTPResponse GET(HTTPRequest request) {
		return new HTTPResponse(200);
	}

	@Override
	public HTTPResponse POST(HTTPRequest request) {
		Random rand = new Random();
		HashMap<Integer, Integer> data = new HashMap<>();
		for (int i = 0; i < 20; i++) {
			data.put(rand.nextInt(100), rand.nextInt(100));
		}
		return new HTTPResponse(200, new JSONObject((Map<Integer, Integer>) data));
	}
}
