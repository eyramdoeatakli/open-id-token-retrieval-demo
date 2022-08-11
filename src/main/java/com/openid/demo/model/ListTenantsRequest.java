package com.openid.demo.model;

import lombok.Data;

@Data
public class ListTenantsRequest {
	
	private String authorizationEndpoint;
	private String tokenEndpoint;
	private String scope;
	private String grantType;
	private String responseType;
}
