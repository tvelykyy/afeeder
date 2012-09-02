package com.tvelykyy.afeeder.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.tvelykyy.afeeder.domain.Activity;

public interface ActivityService {
	List<Activity> listAllActivities();
	List<Activity> listLatestActivities(Long afterId);
	List<Activity> listRangeActivities(Long startId, Long endId);
	Long addActivity(Activity activity);
	List<Activity> findActivities(String pattern);
	void replicateActivitiesToSolr() throws IOException, SolrServerException;
}
