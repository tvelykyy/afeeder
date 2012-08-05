package com.tvelykyy.afeeder.webservice.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;

@Service("activityWebServiceEndpoint")
@WebService(name = "ActivityWebService")
public class ActivityWebServiceEndpoint {
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
