package com.ahannon.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.ahannon.request.HTTPRequest;
import com.ahannon.request.RequestParser;
import com.ahannon.response.HTTPResponse;

class ClientHandler implements Runnable {
	private Socket clientSocket;
	private Router router;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.router = new Router();
	}

	@Override
	public void run() {
		try (InputStream inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream()) {

			/* Parse the incoming request into an HTTPRequest object */
			HTTPRequest request = RequestParser.parse(inputStream);

			/* Route the incoming request to it's desination */
			HTTPResponse response = this.router.route(request);

			/* Write the response to the output stream to return response */
			outputStream.write(response.getResponse().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}