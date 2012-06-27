package com.tvelykyy.afeeder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.ActivityDAO;
import com.tvelykyy.afeeder.domain.Activity;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityDAO activityDAO;

	@Override
	public List<Activity> listAllActivities() {
		return activityDAO.listAllActivities();
	}

	@Override
	public List<Activity> listLatestActivities(Long afterId) {
		return activityDAO.listLatestActivities(afterId);
	}

	@Override
	public Long addActivity(Activity acitivity) {
		return activityDAO.addActivity(acitivity);
	}	
}
