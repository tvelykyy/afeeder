package com.tvelykyy.afeeder.webservice.soap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;
import com.tvelykyy.afeeder.webservice.interceptor.AuthInterceptor;

//@org.apache.cxf.interceptor.InInterceptors (interceptors = {"com.tvelykyy.afeeder.webservice.interceptor.AuthInterceptor" })
@Service("activityWebServiceEndpoint")
@WebService(name = "ActivityWebService")
public class ActivityWebServiceEndpoint {
	@Resource
	WebServiceContext wsctx;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	GroupService groupService;
	
	@WebMethod
	public List<Activity> listAllActivities(){
		return activityService.listAllActivities();
	}
	
	@WebMethod
	public Long addActivity(Activity activity) {
		MessageContext mctx = wsctx.getMessageContext();
		
		Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<Object> tokenList = (List<Object>) http_headers.get(AuthInterceptor.AUTH_TOKEN);
        Long userId = new Long(tokenList.get(0).toString());
        
		//TODO user extraction by token
		activity.setUser(new User(userId));
		return activityService.addActivity(activity);
	}
	
	@WebMethod
	public List<Group> listAllGroups() {
		return groupService.listGroups();
	}
	
	@WebMethod
	public List<Activity> findActivities(String pattern) {
		return activityService.findActivities(pattern);
	}
}
