package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;
import com.tvelykyy.afeeder.web.ActivityController;

public class ActivityControllerTest {
	@Mock
	ActivityService activityService;
	
	@Mock
	GroupService groupService;
	
	@Mock
	SecurityContext securityContext;
	
	@Mock
	Authentication authentication;
	
	@Before
	public void setup() {
		//This must be called for the @Mock annotations above to be processed.
		MockitoAnnotations.initMocks( this );
	}
	
	@Test
	public void listAllActivitiesTest() {
		//Setup mock activity list
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(new Activity(new Long(1), null, null, "TestActivity"));
		when(activityService.listAllActivities()).thenReturn(activities);

		//Setup mock group list
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group(new Long(1), "TestGroup"));
		when(groupService.listGroups()).thenReturn(groups);
		
		//Create instance of controller we want to test
		ActivityController activityController = new ActivityController();
		
		//Manually setting services
		ReflectionTestUtils.setField(activityController, "activityService", activityService);
		ReflectionTestUtils.setField(activityController, "groupService", groupService);
		
		//Calling method under the test
		ModelAndView mav = activityController.listAllActivities(new HashMap<String, Object>());
		
		//Reviewing the results
		assertEquals(activities, mav.getModel().get("activities"));
		assertEquals(groups, mav.getModel().get("groups"));
		
		assertEquals( "activity", mav.getViewName());
	}
}
