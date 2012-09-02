package com.tvelykyy.afeeder.dao;

import java.util.List;

import com.tvelykyy.afeeder.domain.Activity;

public interface ActivityDAO {
	List<Activity> listAllActivities();
	List<Activity> listLatestActivities(Long afterId);
	List<Activity> listRangeActivities(Long startId, Long endId);
	Long addActivity(Activity acitivity);
	List<Activity> findActivities(String pattern);
	Activity getActivityById(Long id);
}
