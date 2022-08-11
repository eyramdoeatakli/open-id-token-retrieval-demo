package com.openid.demo.contoller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openid.demo.model.ListTenantsRequest;
import com.openid.demo.service.APIProxy;
import com.openid.demo.service.BusinessLogic;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ecma-app")
public class Controller {
	
	private APIProxy apiProxy;
	private BusinessLogic businessLogic;
	
	/*
	 * 
	 * 
	 *
	 * */
	
    @RequestMapping(value = "retrieve-auth-and-token-endpoints", method = RequestMethod.GET, produces = { "application/json" })
	public String getAuthorizationAndTokenEndpointInformation() {
		return apiProxy.getAuthorizationAndTokenEndpointInformation();
	}
	
    @RequestMapping(value = "retrieve-list-tenants", method = RequestMethod.POST, produces = { "application/json" })
	public String getListTenantsInformation(@RequestBody ListTenantsRequest retrieveListTenantsRequest) {
		return businessLogic.processListTenantsRequest(retrieveListTenantsRequest);
	}
	

}
