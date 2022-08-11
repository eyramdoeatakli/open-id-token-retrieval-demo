package com.openid.demo.model;

import lombok.Data;

@Data
public class AuthorizeClientResponse {

	private String code;
	private int httpStatusCode;
	private String jsonErrorResponse;

}
