package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.ActivityService;
import com.tvelykyy.afeeder.service.GroupService;
import com.tvelykyy.afeeder.service.UserService;

public class ActivityServiceTest extends BaseTest {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
	private Group group;
	private User user;
	private Activity activity;
	
	@Before
	public void init() {
		user = new User("testuser", "password", "TestUser");
		user.hashPassword();
		user.setId(userService.addUser(user));
		
		group = new Group("TestGroup");
		group.setId(groupService.addGroup(group));
		
		activity = new Activity(user, group, "TestActivity");
	}
	
	@Test
	public void addActivityTest() {		
		//Activity shouldn't have id on this stage
		assertNull(activity.getId());
		
		Long id = activityService.addActivity(activity);
		//Id shouldn't be null
		assertNotNull(id);
	}
	
	@Test 
	public void listAllActivitiesTest() {
		List<Activity> activitiesBeforeAdd = activityService.listAllActivities();
		
		activityService.addActivity(activity);
		
		List<Activity> activitiesAfterAdd = activityService.listAllActivities();
		
		assertEquals(activitiesBeforeAdd.size() + 1, activitiesAfterAdd.size());
	}
	
	@Test
	public void listLatestActivitiesTest() {
		//Adding 3 activities
		Long id1 = activityService.addActivity(activity);
		Long id2 = activityService.addActivity(activity);
		Long id3 = activityService.addActivity(activity);
		
		//Should return 2 activities with ids (id2, id3)
		List<Activity> activities = activityService.listLatestActivities(id1);
		assertEquals(2, activities.size());

	}
	
	@Test
	public void listRangeActivitiesTest() {
		//Adding 4 activities
		Long id1 = activityService.addActivity(activity);
		Long id2 = activityService.addActivity(activity);
		Long id3 = activityService.addActivity(activity);
		Long id4 = activityService.addActivity(activity);
		
		//Should return 2 activities with ids (id2, id3)
		List<Activity> activities = activityService.listRangeActivities(id1, id4);
		assertEquals(2, activities.size());

	}
}