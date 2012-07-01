package com.tvelykyy.afeeder.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles general applications requests.
 */
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		logger.info("Rendering login page");
		
		return "login";
	}
	
	@RequestMapping(value = "/error403", method = RequestMethod.GET)
	public String get403Page() {
		return "error403";
	}
}
