package com.tvelykyy.afeeder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.GroupDAO;
import com.tvelykyy.afeeder.domain.Group;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDAO groupDAO;
	
	public List<Group> listGroups() {
		return groupDAO.listGroups();
	}
	
	public Long addGroup(Group group) {
		return groupDAO.addGroup(group);
	}
	
	public void removeGroup(Long id) {
		groupDAO.removeGroup(id);
	}
	
	public void editGroup(Group group) {
		groupDAO.editGroup(group);
	}
}
