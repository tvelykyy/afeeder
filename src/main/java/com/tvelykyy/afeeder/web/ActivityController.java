package com.tvelykyy.afeeder.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.JsonResponse;
import com.tvelykyy.afeeder.domain.SecurityUser;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;
import com.tvelykyy.afeeder.service.UserService;

/**
 * Handles requests for the application activity logic.
 */
@Controller
public class ActivityController {
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	/**
	 * Returns all activities
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView listAllActivities(Map<String, Object> model) {
		logger.info("ActivityController: Loading activity list");
		
		
		ModelAndView mav = new ModelAndView("activity");
		
		List<Activity> activities = activityService.listAllActivities();
		mav.addObject("activities", activities);
		
		List<Group> groups = groupService.listGroups();
		mav.addObject("groups", groups);
		
		model.put("activity", new Activity());
		return mav;
	}
	
	@RequestMapping(value = "activity/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse addGroup(@ModelAttribute(value="activity") Activity activity, 
			BindingResult result){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		activity.setGroup(groupService.getGroup(activity.getGroup().getId()));
		activity.setUser(userService.getUserById(((SecurityUser)userDetails).getId()));
		
		logger.info("Adding new activity = " + activity);
		
		JsonResponse res = new JsonResponse();
		
		if(!result.hasErrors()){
			Long id = activityService.addActivity(activity);
			activity.setId(id);
			res.setSuccess(activity);
		} else{
			res.setFail(result.getAllErrors());
		}
			 
		return res;
	}
}
