package com.itwillbs.login;

public interface SnsUrl {
	// 토큰을 받아오는 end-point
	static final String NAVER_ACCESS_TOKEN = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	// 인증을 받는 url
	static final String NAVER_AUTH = "https://nid.naver.com/oauth2.0/authorize";
	// 액세스 토큰을 가지고 프로필을 가지러 가는 url
	static final String NAVER_PORFILE_URL = "https://openapi.naver.com/v1/nid/me";
	
}
