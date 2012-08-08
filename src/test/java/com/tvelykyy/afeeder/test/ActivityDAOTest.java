package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.dao.ActivityDAO;
import com.tvelykyy.afeeder.dao.GroupDAO;
import com.tvelykyy.afeeder.dao.UserDAO;
import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.domain.utils.UserUtils;

public class ActivityDAOTest extends BaseTest {
	@Autowired
	private ActivityDAO activityDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GroupDAO groupDAO;
	
	private Group group;
	private User user;
	private Activity activity;
	
	@Before
	public void init() {
		user = new User("testuser", "password", "TestUser");
		user.setPassword(UserUtils.hashMD5(user.getPassword()));
		user.setId(userDAO.addUser(user));
		
		group = new Group("TestGroup");
		group.setId(groupDAO.addGroup(group));
		
		activity = new Activity(user, group, "TestActivity");
	}
	
	@Test
	public void addActivityTest() {		
		//Activity shouldn't have id on this stage
		assertNull(activity.getId());
		
		Long id = activityDAO.addActivity(activity);
		//Id shouldn't be null
		assertNotNull(id);
	}
	
	@Test 
	public void listAllActivitiesTest() {
		List<Activity> activitiesBeforeAdd = activityDAO.listAllActivities();
		
		activityDAO.addActivity(activity);
		
		List<Activity> activitiesAfterAdd = activityDAO.listAllActivities();
		
		assertEquals(activitiesBeforeAdd.size() + 1, activitiesAfterAdd.size());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void listLatestActivitiesTest() {
		//Adding 3 activities
		Long id1 = activityDAO.addActivity(activity);
		Long id2 = activityDAO.addActivity(activity);
		Long id3 = activityDAO.addActivity(activity);
		
		//Should return 2 activities with ids (id2, id3)
		List<Activity> activities = activityDAO.listLatestActivities(id1);
		assertEquals(2, activities.size());

	}
	
	@SuppressWarnings("unused")
	@Test
	public void listRangeActivitiesTest() {
		//Adding 4 activities
		Long id1 = activityDAO.addActivity(activity);
		Long id2 = activityDAO.addActivity(activity);
		Long id3 = activityDAO.addActivity(activity);
		Long id4 = activityDAO.addActivity(activity);
		
		//Should return 2 activities with ids (id2, id3)
		List<Activity> activities = activityDAO.listRangeActivities(id1, id4);
		assertEquals(2, activities.size());

	}
}