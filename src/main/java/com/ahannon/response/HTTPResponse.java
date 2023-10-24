package com.ahannon.response;

public class HTTPResponse {
	@SuppressWarnings("unused")
	private Integer status;

	public HTTPResponse(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

}
