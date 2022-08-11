package com.openid.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
public class ApplicationProperties {
	
	private String serviceDiscoveryEndpoint;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String openIdScope;
	private String listTenantUrl;
	

}
