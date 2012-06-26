package com.tvelykyy.afeeder.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.jta.UserTransactionAdapter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.JsonResponse;
import com.tvelykyy.afeeder.domain.Role;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.GroupService;
import com.tvelykyy.afeeder.service.UserService;


/**
 * Handles requests for the application group logic.
 */
@Controller
@RequestMapping("/user")
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="user") User user, 
			BindingResult result){
		logger.info("UserController: Adding new user" + user);
		
		ValidationUtils.rejectIfEmpty(result, "name", "Name can not be empty.");
		
		if(!result.hasErrors()){
			user.hashPassword();
			Long id = userService.addUser(user);
			user.setRoles(userService.getUserRoles(user.getLogin()));
			
			Collection authorities = new ArrayList();
			for (Role role : user.getRoles()) {
				authorities.add(new GrantedAuthorityImpl(role.getName()));
			}
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken( user.getLogin(), user.getPassword(), 
							authorities);
		    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			return "redirect:/";
		} else{
			return "/register";
		}
	}
}
