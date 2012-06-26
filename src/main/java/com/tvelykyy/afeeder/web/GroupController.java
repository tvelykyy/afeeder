package com.tvelykyy.afeeder.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.tvelykyy.afeeder.service.GroupService;


/**
 * Handles requests for the application group logic.
 */
@Controller
@RequestMapping("/group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	/**
	 * Returns all groups
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView list() {
		logger.info("GroupController: Loading group list");
		ModelAndView mav = new ModelAndView("group");
		List<Group> groups = groupService.listGroups();
		mav.addObject("groups", groups);
		return mav;
	}
	
	/**
	 * Adds new group
	 * @param group
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse add(@ModelAttribute(value="group") Group group, 
			BindingResult result ){
		logger.info("GroupController: Adding new group");
		
		JsonResponse res = new JsonResponse();
		ValidationUtils.rejectIfEmpty(result, "name", "Name can not be empty.");
		
		if(!result.hasErrors()){
			Long id = groupService.addGroup(group);
			group.setId(id);

			res.setSuccess(group);
		} else{
			res.setFail(result.getAllErrors());
		}
			 
		return res;
	}
	
	/**
	 * Removes group
	 */
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody JsonResponse remove(@RequestParam Long id) {
		logger.info("GroupController: Removing group id = " + id);
		
		JsonResponse res = new JsonResponse();
		try {
			groupService.removeGroup(id);
			res.setSuccess(null);
		} catch (Exception e){
			logger.warn(e.toString());
			res.setFail(null);
		}
		
		return res;
	}
	
	/**
	 * Edits group
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody JsonResponse edit(@ModelAttribute(value="group") Group group, 
			BindingResult result ){
		logger.info("GroupController: Editing group id = " + group.getId());
		
		JsonResponse res = new JsonResponse();
		ValidationUtils.rejectIfEmpty(result, "name", "Name can not be empty.");
		
		if(!result.hasErrors()){
			groupService.editGroup(group);
			res.setSuccess(group);
		} else{
			res.setFail(result.getAllErrors());
		}
			 
		return res;
	}
}
