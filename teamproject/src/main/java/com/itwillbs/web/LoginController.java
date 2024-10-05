package com.itwillbs.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// http://localhost:8088/web/index/login
	@RequestMapping(value = "/index/login", method = RequestMethod.GET)
	public String home() {
		logger.info(" Login page ");
		
		
		return "login";
	}
	
}
