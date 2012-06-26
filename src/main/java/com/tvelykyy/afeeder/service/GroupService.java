package com.tvelykyy.afeeder.service;

import java.util.List;

import com.tvelykyy.afeeder.domain.Group;

public interface GroupService {
	List<Group> listGroups();
	Long addGroup(Group group);
	void removeGroup(Long id);
	void editGroup(Group group);
}
