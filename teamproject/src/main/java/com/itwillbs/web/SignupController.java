package com.itwillbs.web;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.pass.EncryptionUtil;
import com.itwillbs.service.MemberService;

@Controller
public class SignupController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	private static SecretKey secretKey;
	
	static {
        try {
            secretKey = EncryptionUtil.generateKey(); // 비밀 키 생성
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
            // 예외 발생 시 로그를 남기거나 기본 키를 설정하는 등의 추가 조치를 취할 수 있습니다.
        }
    }
	
	@Inject
	private MemberService service;
	// http://localhost:8088/signup
	@RequestMapping(value ="signup" , method=RequestMethod.GET)
	public void signupGet() {
		logger.info("signupPage get 실행 ");
	}
	// http://localhost:8088/signup
	@RequestMapping(value ="signup" , method=RequestMethod.POST)
	public String signUpPost(MemberVO vo) {
		logger.info("signupPage POST 실행 ");
		
		
		logger.info("vo : "+ vo);
		MemberVO vo2 = service.memberInfoTel(vo.getMember_tel());
		if(vo2 != null) {
			return "redirect:/signup"; // 중복된 전화번호
		}
		
		try {
			String encryptedInput = EncryptionUtil.encrypt(vo.getMember_pw(), secretKey);
			vo.setMember_pw(encryptedInput); // 암호화된 비밀번호를 설정	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 회원가입 실행.
		service.memberJoin(vo);
		
		
		return "redirect:/login";
	}
	
	@RequestMapping(value ="/checkUserId" , method=RequestMethod.POST)
	public ResponseEntity<Map<String, String>> memberIdcheck(@RequestBody MemberVO vo) {
		logger.info(" Vo 검사 : "+vo);
		logger.info("memberId 중복검사 : "+vo.getMember_id() );
		String member_id = vo.getMember_id();
		
		MemberVO vo2 =  service.memberInfo(member_id);
		Map<String, String> response = new HashMap<>();
		
		if(vo2 == null) {
			response.put("message", "사용가능한 아이디 입니다!");
			return ResponseEntity.ok(response); // 200 OK 응답  
    	}
    	
		response.put("message", "이미 등록된 아이디 입니다! 다른 아이디를 입력해주세요.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);// 400 BAD REQUEST 응답  
        
	}
	
}
