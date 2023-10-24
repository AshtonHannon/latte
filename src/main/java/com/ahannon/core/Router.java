package com.ahannon.core;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import com.ahannon.annotations.Route;
import com.ahannon.request.HTTPRequest;
import com.ahannon.response.HTTPResponse;
import com.ahannon.views.View;

public class Router {
	private HashMap<String, View> routeMap;

	public Router() {
		/*
		 * When a router is instatiated use reflections to get all of the routes for the
		 * views
		 * and use them to initialize the routeMap
		 */
		this.routeMap = new HashMap<>();
		Set<Class<?>> classes = new Reflections("com.ahannon.views").getTypesAnnotatedWith(Route.class);
		for (Class<?> cls : classes) {
			try {
				this.routeMap.put(cls.getAnnotation(Route.class).route(), (View) cls.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public HTTPResponse route(HTTPRequest request) {
		String route = request.getRoute();
		View view = this.routeMap.get(route);

		/* Ignore favicon */
		if (route.equals("/favicon.ico")) {
			return new HTTPResponse(200);
		}

		/* Execute the proper function based on the request method */
		switch (request.getHTTPMethod()) {
			case "POST":
				return view.POST(request);
			default:
				return view.GET(request);
		}
	}
}
