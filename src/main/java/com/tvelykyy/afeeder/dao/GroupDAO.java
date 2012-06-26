package com.tvelykyy.afeeder.dao;

import java.util.List;

import com.tvelykyy.afeeder.domain.Group;

public interface GroupDAO {
	List<Group> listGroups();
	Long addGroup(Group group);
	void removeGroup(Long id);
	void editGroup(Group group);
}
