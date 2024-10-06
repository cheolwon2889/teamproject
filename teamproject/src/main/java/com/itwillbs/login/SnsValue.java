package com.itwillbs.login;

import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;


@Data
public class SnsValue implements SnsUrl{
	
	private String service; // naver
	private String clientId; // naver
	private String clientSecret; // naver
	private String redirectUrl; // naver
	private DefaultApi20 api20Instance; // naverAPI20내부에 정의된 인스턴스를 가져오기 위해 상위 클래스인 DefaultAPI20타입으로 설정한다.
	// SnsUrl에서 naverURL을 가져올 수 있다.
	
	private String profileUrl; // 각 소셜에서 프로필을 받아올 URL을 저장
	
	private boolean isNaver;
	
	public SnsValue(String service , String clientId, String clientSecret , String redirectUrl) {
		super();
		this.service = service;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		this.isNaver = this.service.equals("naver");
		
		if(isNaver) {
			this.api20Instance = NaverAPI20.Instance();
			this.profileUrl = NAVER_PORFILE_URL;
		}
	}
	
	
	
	

}
