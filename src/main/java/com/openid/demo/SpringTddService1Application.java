package com.openid.demo;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringTddService1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringTddService1Application.class, args);
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		return restTemplate;
//	}
	
//	  @Bean
//	    public RestTemplate httpClient(RestTemplateBuilder builder) {
//	        return builder
//	                .setConnectTimeout(Duration.ofSeconds(10))
//	                .setReadTimeout(Duration.ofSeconds(10))
//	                .requestFactory(CustomClientHttpRequestFactory.class)
//	                .build();
//	    }
//	
	@Bean
    public RestTemplate restTemplate() {
//		
//		HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
//		RestTemplate restTemplate=new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
	//	HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy()).build();
	    HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();

		factory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate();
		 restTemplate.setRequestFactory(factory);
		 return restTemplate;
       
		
//		RestTemplate restTemplate = new RestTemplate();
//        final HttpComponentsClientHttpRequestFactory factory = 
//                 new HttpComponentsClientHttpRequestFactory();
//        CloseableHttpClient build = 
//                 HttpClientBuilder.create().disableRedirectHandling().build();
//        factory.setHttpClient(build);
//        restTemplate.setRequestFactory(factory);
//        return restTemplate;
    }

}
