package com.openid.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.openid.demo.model.AuthorizeClientResponse;
import com.openid.demo.model.ListTenantsRequest;
import com.openid.demo.model.TokenResponse;
import com.openid.demo.model.TokenResponseWrapper;
import com.openid.demo.properties.ApplicationProperties;
import com.openid.demo.utils.JsonUtility;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class APIProxy {
	
	private RestTemplate restTemplate;
	private ApplicationProperties applicationProperties;
	
	public  String getAuthorizationAndTokenEndpointInformation() {
		String endpoints=restTemplate.getForObject(applicationProperties.getServiceDiscoveryEndpoint(), String.class);
		log.info("Endpoint info {}",endpoints);
		 return endpoints;
	}
	
	

	
	public AuthorizeClientResponse authorizeClient(ListTenantsRequest listTenantsRequest) {
         final String url = listTenantsRequest.getAuthorizationEndpoint()+"?response_type="+listTenantsRequest.getResponseType()+
        		 "&scope="+applicationProperties.getOpenIdScope()+"%20"+listTenantsRequest.getScope()+"&client_id="+applicationProperties.getClientId()+"&redirect_uri="+applicationProperties.getRedirectUri();
       

         Unirest.config()
         .connectTimeout(60000)
         .setDefaultHeader("Accept", "application/json")
         .followRedirects(false)
         .enableCookieManagement(false);

         AuthorizeClientResponse authorizeClientResponse = new AuthorizeClientResponse();
         HttpResponse<String> authResponse=null;;
		try {
			authResponse = Unirest.get(url)
					 .asString();
		} catch (Exception e) {
			log.error("unirest exception {}",e);
			authorizeClientResponse.setJsonErrorResponse("{\n"
					+ "    \"errorMessage\": \"Unable to reach service. Try again later\","
					+ "\n"
					+ "}");
		}

         log.info("response unirest{}",authResponse.getHeaders());
    
         
         authorizeClientResponse.setCode(authResponse.getHeaders().toString());
         authorizeClientResponse.setHttpStatusCode(authResponse.getStatus());
         return authorizeClientResponse;	
	}

	
	
	public TokenResponseWrapper retrieveToken(ListTenantsRequest listTenantsRequest,String code) {  
        TokenResponse tokenResponse= new TokenResponse();
		TokenResponseWrapper wrapper= new TokenResponseWrapper();

		try {
        HttpResponse<String> tokenResponseEntity = Unirest.post(listTenantsRequest.getTokenEndpoint())
        		  .header("content-type", "application/x-www-form-urlencoded")
        		  .header("Authorization", "Basic YS51bmlxdWUuY2xpZW50LmlkLnN0cmluZzphLnVuaXF1ZS5jbGllbnQuc2VjcmV0LnN0cmluZw==")
        		  .body("grant_type=+"+listTenantsRequest.getGrantType()+"&code="+code+"&redirect_uri="+applicationProperties.getRedirectUri())
        		  .asString();

        log.info("token headers{}",tokenResponseEntity.getHeaders());
        log.info("token body{}",tokenResponseEntity.getBody());
	
		
		wrapper.setHttpStatusCode(tokenResponseEntity.getStatus());
		wrapper.setTokenResponse(tokenResponseEntity.getBody().toString());
			
		} catch (Exception e) {
		wrapper.setHttpStatusCode(400);
		log.error("token exception {}",e.getMessage());
		} 
        return wrapper;	
	}
	
	
	public String getlistTenantsResponse(TokenResponse tokenResponse) {
		final String url =applicationProperties.getListTenantUrl();
		HttpResponse<String> responses = Unirest.get(url)
				.header("Authorization", "Bearer "+tokenResponse.getId_token())
				.asString();
		return responses.getBody();
	}
	

}
