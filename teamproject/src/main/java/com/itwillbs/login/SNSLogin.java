package com.itwillbs.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.itwillbs.domain.UserVO;

public class SNSLogin {
	
	
	private OAuth20Service oauthService;
	private String profileUrl;
	
	public SNSLogin(SnsValue sns) {
		this.oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
				.scope("profile")
				.build(sns.getApi20Instance());
		
		this.profileUrl = sns.getProfileUrl();
	}
	
	public String getNaverAuthURL() {
		return this.oauthService.getAuthorizationUrl();	
	}
	
	public UserVO getUserProfile(String code) throws Exception {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, this.profileUrl);
		oauthService.signRequest(accessToken, request);
		
		Response response = oauthService.execute(request);
		
		return parseJson(response.getBody());
	}
	
	public UserVO parseJson(String body) throws Exception {
		System.out.println(body);
		
		UserVO user = new UserVO();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);
		
		// 네이버 json 파싱
		JsonNode resNode = rootNode.get("response");
		user.setId(resNode.get("id").asText());
		user.setName(resNode.get("name").asText());
		user.setGender(resNode.get("gender").asText());
		user.setEmail(resNode.get("email").asText());
		user.setTel(resNode.get("mobile").asText());
		user.setBirth(resNode.get("birthyear").asText() + "-" +resNode.get("birthday").asText());
		
		return user;
		
	}

}
