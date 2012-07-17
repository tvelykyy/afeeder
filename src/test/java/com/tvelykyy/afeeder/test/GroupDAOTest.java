package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.dao.GroupDAO;
import com.tvelykyy.afeeder.domain.Group;

public class GroupDAOTest extends BaseTest {
	@Autowired
	private GroupDAO groupDAO;
	
	@Test
	public void addGroupTest() {
		Group group = new Group("TestGroup");
		//New group shouldn't have id
		assertNull(group.getId());
		
		group.setId(groupDAO.addGroup(group));
		//Persisted group should have id
		assertNotNull(group.getId());
		
		Group groupFromDB = groupDAO.getGroup(group.getId());
		//We should be able to get group by newly created id
		assertNotNull(groupFromDB);
		//Whether groupFromDB name equals
		assertEquals(group.getName(), groupFromDB.getName());
	}
	
	@Test
	public void listGroupsTest() {
		List<Group> groupsBeforeAdd = groupDAO.listGroups();
		
		Group group = new Group("TestGroup");
		groupDAO.addGroup(group);
		
		List<Group> groupsAfterAdd = groupDAO.listGroups();
		
		//Group list should have +1 size after adding new group
		assertEquals(groupsBeforeAdd.size() + 1, groupsAfterAdd.size());
	}
	
	@Test
	public void removeGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupDAO.addGroup(group));
		
		groupDAO.removeGroup(group.getId());
		
		//There should no group
		assertNull(groupDAO.getGroup(group.getId()));
	}
	
	@Test
	public void editGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupDAO.addGroup(group));
		
		group.setName("EditedTestGroup");
		groupDAO.editGroup(group);
		
		Group editedGroup = groupDAO.getGroup(group.getId());
		
		//Names should be equal
		assertEquals(group.getName(), editedGroup.getName());
	}
	
	@Test
	public void getGroupTest() {
		Group group = new Group("TestGroup");
		group.setId(groupDAO.addGroup(group));
		
		Group newGroup = groupDAO.getGroup(group.getId());
		//Group should be null
		assertNotNull(newGroup);
		
		//Properties should be equal
		assertEquals(group.getId(), newGroup.getId());
		assertEquals(group.getName(), newGroup.getName());
	}
}