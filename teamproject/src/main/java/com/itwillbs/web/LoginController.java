package com.itwillbs.web;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.domain.UserVO;
import com.itwillbs.login.SNSLogin;
import com.itwillbs.login.SnsValue;
import com.itwillbs.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
	private SnsValue naverSns;
	
	@Inject
	private UserService service;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// http://localhost:8088/web/index/login
	@RequestMapping(value = "/index/login", method = RequestMethod.GET)
	public String snsLogin(Model model) {
		logger.info(" Login page ");
		SNSLogin snsLogin = new SNSLogin(naverSns);
		
		model.addAttribute("naver_url", snsLogin.getNaverAuthURL());
		
		return "login";
	}
	
	@RequestMapping( value ="/auth/naver/callback" , method =RequestMethod.GET)
	public String loginCallback(Model model , @RequestParam String code, HttpSession session) throws Exception {
		
		SNSLogin snsLogin = new SNSLogin(naverSns);
		
		UserVO snsUser = snsLogin.getUserProfile(code);
		
		logger.info("Profile >> " + snsUser);
		
		// 3. db
		UserVO user = service.getBySns(snsUser);
		if(user == null ) {
			model.addAttribute("result", "존재하지 않는 사용자 입니다. 가입해 주세요.");
			// 회원가입 페이지로
			// returm=n "singup";
		} else {
			model.addAttribute("result", user.getName() + "님 반갑습니다." );
			session.setAttribute("id", user);
			
		}
		
		return "main";
	}
	
}
