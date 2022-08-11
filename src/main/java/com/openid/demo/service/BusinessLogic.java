package com.openid.demo.service;

import org.springframework.stereotype.Service;

import com.openid.demo.model.AuthorizeClientResponse;
import com.openid.demo.model.ListTenantsRequest;
import com.openid.demo.model.TokenResponse;
import com.openid.demo.model.TokenResponseWrapper;
import com.openid.demo.utils.JsonUtility;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessLogic {
	
	private APIProxy apiProxy;
	
	public String processListTenantsRequest(ListTenantsRequest listTenantsRequest) {
		String listTenantResponse="";
		AuthorizeClientResponse authorizeClientResponse=apiProxy.authorizeClient(listTenantsRequest);
		if(302==(authorizeClientResponse.getHttpStatusCode())) {
			
			String code = parseAuthorizationResponseToRetrieveCode(authorizeClientResponse);
			TokenResponseWrapper tokenResponseWrapper= apiProxy.retrieveToken(listTenantsRequest, code);
			
			
			if(200==(authorizeClientResponse.getHttpStatusCode())) {
				TokenResponse tokenResponse=new TokenResponse();
				try {
					tokenResponse = JsonUtility.fromJson(tokenResponseWrapper.getTokenResponse(), TokenResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				return listTenantResponse=apiProxy.getlistTenantsResponse(tokenResponse);
			}	else {
				
				return tokenResponseWrapper.getTokenResponse();		
			}		

			
		

		}else { 
			String jsonErrorStr=authorizeClientResponse.getJsonErrorResponse();
			return jsonErrorStr;
		}
		
		
	}

	private String parseAuthorizationResponseToRetrieveCode(AuthorizeClientResponse authorizeClientResponse) {
		String codeToProcess[]=authorizeClientResponse.getCode().split("=");
		String codeToProcess1=codeToProcess[1].toString();
		String codeToProcess2[]=codeToProcess1.split("server:");
		String code=codeToProcess2[0];
		log.info("code {}",code);
		return code;
	}

}
