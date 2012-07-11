package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.service.GroupService;

public class GroupServiceTest extends BaseTest {
	@Autowired
	private GroupService groupService;
	
	@Test
	public void addGroupTest() {
		Group group = new Group("TestGroup");
		//New group shouldn't have id
		assertNull(group.getId());
		
		group.setId(groupService.addGroup(group));
		//Persisted group should have id
		assertNotNull(group.getId());
		
		Group groupFromDB = groupService.getGroup(group.getId());
		//We should be able to get group by newly created id
		assertNotNull(groupFromDB);
		//Whether groupFromDB name equals
		assertEquals(group.getName(), groupFromDB.getName());
	}
	
	@Test
	public void listGroupsTest() {
		List<Group> groupsBeforeAdd = groupService.listGroups();
		
		Group group = new Group("TestGroup");
		groupService.addGroup(group);
		
		List<Group> groupsAfterAdd = groupService.listGroups();
		
		//Group list should have +1 size after adding new group
		assertEquals(groupsBeforeAdd.size() + 1, groupsAfterAdd.size());
	}
	
	@Test
	public void removeGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupService.addGroup(group));
		
		groupService.removeGroup(group.getId());
		
		//There should no group
		assertNull(groupService.getGroup(group.getId()));
	}
	
	@Test
	public void editGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupService.addGroup(group));
		
		group.setName("EditedTestGroup");
		groupService.editGroup(group);
		
		Group editedGroup = groupService.getGroup(group.getId());
		
		//Names should be equal
		assertEquals(group.getName(), editedGroup.getName());
	}
	
	@Test
	public void getGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupService.addGroup(group));
		
		Group newGroup = groupService.getGroup(group.getId());
		//Group should be null
		assertNotNull(newGroup);
		
		//Properties should be equal
		assertEquals(group.getId(), newGroup.getId());
		assertEquals(group.getName(), newGroup.getName());
	}
}