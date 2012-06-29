package com.tvelykyy.afeeder.service;

import java.util.List;

import com.tvelykyy.afeeder.domain.Activity;
import com.tvelykyy.afeeder.domain.Group;

public interface ActivityService {
	public List<Activity> listAllActivities();
	public List<Activity> listLatestActivities(Long afterId);
	public List<Activity> listRangeActivities(Long startId, Long endId);
	public Long addActivity(Activity acitivity);
}
