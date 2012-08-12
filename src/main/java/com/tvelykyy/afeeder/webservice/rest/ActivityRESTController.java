package com.tvelykyy.afeeder.webservice.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.JsonResponse;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;

/**
 * Handles all REST logic
 * @author tvelykyy
 */
@Controller
@RequestMapping("/rest")
public class ActivityRESTController {
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private GroupService groupService;
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityRESTController.class);
	
	@RequestMapping(value = "activities", method = RequestMethod.GET, 
			produces = "application/json")
	@ResponseBody
	public JsonResponse listAllActivities() {
		logger.debug("REST - retrivieving all activities");
		List<Activity> activities = activityService.listAllActivities();
		
		JsonResponse response = new JsonResponse();
		response.setSuccess(activities);
		
		return response;
	}
	
	@RequestMapping(value = "activities/search/{pattern}", method = RequestMethod.GET,
			produces = "application/json")
	@ResponseBody
	public JsonResponse findActivities(@PathVariable String pattern) {
		logger.debug(MessageFormatter.format("REST - searching activities by pattern {}", pattern));
		List<Activity> activities = activityService.findActivities(pattern);
		
		JsonResponse response = new JsonResponse();
		response.setSuccess(activities);
		
		return response;
	}
	
	@RequestMapping(value = "groups", method = RequestMethod.GET,
			produces = "application/json")
	@ResponseBody
	public JsonResponse listAllGroups() {
		logger.debug("REST - retrivieving all groups");
		List<Group> groups = groupService.listGroups();
		
		JsonResponse response = new JsonResponse();
		response.setSuccess(groups);
		
		return response;
	}
	
	@RequestMapping(value = "add/activity", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody
	public JsonResponse addActivity(@ModelAttribute Activity activity) {
		JsonResponse response = new JsonResponse();
		try {
			activity.setUser(new User(1L));
			
			logger.debug(MessageFormatter.format("Adding new activity {}", activity));
			
			long id = activityService.addActivity(activity);
			
			response.setSuccess(id);
		} catch (Exception e) {
			response.setFail(e);
		}
		return response;
	}
}
