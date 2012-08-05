package com.tvelykyy.afeeder.dao;

import java.util.List;

import com.tvelykyy.afeeder.domain.Activity;

public interface ActivityDAO {
	public List<Activity> listAllActivities();
	public List<Activity> listLatestActivities(Long afterId);
	public List<Activity> listRangeActivities(Long startId, Long endId);
	public Long addActivity(Activity acitivity);
	public List<Activity> findActivities(String pattern);
}
