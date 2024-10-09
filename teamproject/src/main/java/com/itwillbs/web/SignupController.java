package com.itwillbs.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.service.MemberService;

@Controller
public class SignupController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	
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
