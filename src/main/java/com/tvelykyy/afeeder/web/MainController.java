package com.tvelykyy.afeeder.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tvelykyy.afeeder.domain.User;

/**
 * Handles general applications requests.
 */
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		logger.info("MainController: rendering login page");
		
		return "login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String getRegisterPage(Map<String, Object> model) {
		logger.info("MainController: rendering signup page");
		model.put("user", new User());
		return "signup";
	}
}
