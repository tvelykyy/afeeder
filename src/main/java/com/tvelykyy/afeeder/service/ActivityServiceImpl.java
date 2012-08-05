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

	public List<Activity> listAllActivities() {
		return activityDAO.listAllActivities();
	}

	public List<Activity> listLatestActivities(Long afterId) {
		return activityDAO.listLatestActivities(afterId);
	}

	public List<Activity> listRangeActivities(Long startId, Long endId) {
		return activityDAO.listRangeActivities(startId, endId);
	}
	
	public Long addActivity(Activity acitivity) {
		return activityDAO.addActivity(acitivity);
	}	
	
	public List<Activity> findActivities(String pattern) {
		return activityDAO.findActivities(pattern);
	}
}
