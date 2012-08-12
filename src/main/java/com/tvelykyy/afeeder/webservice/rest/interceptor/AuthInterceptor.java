package com.tvelykyy.afeeder.webservice.rest.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.UserService;
import com.tvelykyy.afeeder.webservice.Const;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
		//Check for wadl request
		if (request.getRequestURI().contains("/rest/wadl")) {
			return true;
		}
		String token = request.getHeader(Const.AUTH_TOKEN);
		
		if (token == null) {
			return send403Error(response);
		}
		
		User user = userService.getUserByToken(token);
		if (user == null) {
			return send403Error(response);
		}
		
		boolean isTokenValid = userService.isTokenValid(user.getId());
		if (!isTokenValid) {
			return send403Error(response);
		}
		
		request.setAttribute(Const.AUTH_TOKEN, user.getId());
		
		return true;
	}

	private boolean send403Error(HttpServletResponse response)
			throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, Const.INVALID_TOKEN);
		return false;
	}
}
