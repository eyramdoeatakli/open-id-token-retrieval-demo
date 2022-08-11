package com.openid.demo.model;

import lombok.Data;

@Data
public class TokenResponseWrapper {
	
	private String tokenResponse;
	private int httpStatusCode;

}
