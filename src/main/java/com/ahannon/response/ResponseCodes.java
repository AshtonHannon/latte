package com.ahannon.response;

import java.util.HashMap;

public class ResponseCodes {
	public static final HashMap<Integer, String> CODES = new HashMap<>();

	static {
		CODES.put(200, "OK");
		CODES.put(404, "Not Found");
		CODES.put(405, "Method Not Alllowed");
	}
}
