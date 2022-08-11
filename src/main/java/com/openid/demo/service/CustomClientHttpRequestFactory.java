package com.openid.demo.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class CustomClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
	 @Override
	    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
	        super.prepareConnection(connection, httpMethod);
	        connection.setInstanceFollowRedirects(false);
	    }
}
