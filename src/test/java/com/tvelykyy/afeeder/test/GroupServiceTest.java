package com.tvelykyy.afeeder.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tvelykyy.afeeder.domain.Group;
import com.tvelykyy.afeeder.service.GroupService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class GroupServiceTest {
	@Autowired
	private GroupService groupService;
	
	@Test
	public void add() {
		Group group = new Group("TestGroup");
		//New group shouldn't have id
		assertNull(group.getId());
		
		group.setId(groupService.addGroup(group));
		//Persisted group should have id
		assertNotNull(group.getId());
		
		//We should be able to get group by newly created id
		assertNotNull(groupService.getGroup(group.getId()));
	}
}