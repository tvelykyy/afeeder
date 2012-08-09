package com.tvelykyy.afeeder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.JsonResponse;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.SecurityUser;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.utils.UserUtils;
import com.tvelykyy.afeeder.domain.validation.ActivityValidator;
import com.tvelykyy.afeeder.domain.validation.UserValidator;
import com.tvelykyy.afeeder.service.UserService;


/**
 * Handles requests for the application group logic.
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	/**
	 * Adds new group
	 * @param group
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="user") User user, 
			BindingResult result){
		logger.debug(MessageFormatter.format("Adding new user {}", user));
		
		new UserValidator().validate(user, result);
		
		if(!result.hasErrors()){
			user.setPassword(UserUtils.hashMD5(user.getPassword()));
			Long id = userService.addUser(user);
			user.setRoles(userService.getUserRolesById(id));
			
			List<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>();
			for (Role role : user.getRoles()) {
				authorities.add(new GrantedAuthorityImpl(role.getName()));
			}
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken( user.getLogin(), user.getPassword(), 
							authorities);
		    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			return "redirect:/";
		} else{
			return "signup";
		}
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String getRegisterPage(Map<String, Object> model) {
		logger.debug("Rendering signup page");
		model.put("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	public String getUserInfoPage(Map<String, Object> model) {
		logger.debug("Rendering user info page");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			user = userService.getUserById(((SecurityUser)userDetails).getId(), false, true);
		//could be string with login
		} else {
			String login = (String) principal;
			user = userService.getUserByLogin(login, false, true);
		}
		
		model.put("user", user);
		
		boolean tokenActive = userService.isTokenValid(user.getId());
		model.put("tokenActive", tokenActive);
		return "userinfo";
	}
	
	@RequestMapping(value = "user/generatetoken", method = RequestMethod.POST)
	public @ResponseBody JsonResponse generateToken(){
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			user = userService.getUserById(((SecurityUser)userDetails).getId(), false, true);
		//could be string with login
		} else {
			String login = (String) principal;
			user = userService.getUserByLogin(login, false, true);
		}
		
		logger.debug(MessageFormatter.format("Generating new token for user = {}", user));
		
		JsonResponse res = new JsonResponse();
		
		user = userService.generateToken(user);
		res.setSuccess(user);
			 
		return res;
	}
}
