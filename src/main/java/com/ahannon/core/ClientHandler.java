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
			HTTPResponse res = this.router.route(request);

			// Send a basic HTTP response back to the client
			String response = "HTTP/1.1 200 OK\r\nContent-Length: 12\r\n\r\nHello, World!";
			outputStream.write(response.getBytes());

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