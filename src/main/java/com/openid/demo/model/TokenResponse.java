package com.openid.demo.model;

import lombok.Data;

@Data
public class TokenResponse {
	
	 private String access_token;
	 private String token_type;
	 private String refresh_token;
	 private float expires_in;
	 private String id_token;


}
