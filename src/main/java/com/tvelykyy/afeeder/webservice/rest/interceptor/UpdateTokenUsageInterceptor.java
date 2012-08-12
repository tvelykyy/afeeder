package com.tvelykyy.afeeder.webservice.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tvelykyy.afeeder.service.UserService;
import com.tvelykyy.afeeder.webservice.Const;

public class UpdateTokenUsageInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
		if (!request.getRequestURI().contains("/rest/wadl")) {
			long userId = (Long) request.getAttribute(Const.AUTH_TOKEN);
			userService.updateTokenUsage(userId);
		}
	}

}
