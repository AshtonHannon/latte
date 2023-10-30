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
				/* Get the route declaration from the 'Route' annotation */
				String routeDeclaration = cls.getAnnotation(Route.class).route();

				/* Get the path of the class after the 'views.' folder */
				String relativeDirectory = cls.getName().split("views.")[1];

				/* Replace all '.' with '/' */
				relativeDirectory = relativeDirectory.replaceAll("\\.", "/");

				/* Remove the name of the class */
				relativeDirectory = relativeDirectory.replace(cls.getSimpleName(), "");

				/* Add the route declaration */
				relativeDirectory = relativeDirectory + routeDeclaration;

				/* Add leading '/' if needed */
				if (!relativeDirectory.substring(0, 1).equals("/")) {
					relativeDirectory = "/" + relativeDirectory;
				}

				/* Add the correlation of this directory to a new instance of the class */
				this.routeMap.put(relativeDirectory, (View) cls.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public HTTPResponse route(HTTPRequest request) {
		String route = request.getRoute();
		View view = this.routeMap.get(route);

		/* Return error if there is no view for the requested route */
		if (view == null) {
			return new HTTPResponse(404);
		}

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
