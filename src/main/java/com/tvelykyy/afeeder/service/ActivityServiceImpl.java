package com.tvelykyy.afeeder.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvelykyy.afeeder.dao.ActivityDAO;
import com.tvelykyy.afeeder.dao.ActivityDAOImpl;
import com.tvelykyy.afeeder.domain.Activity;

@Service
public class ActivityServiceImpl implements ActivityService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
	
	@Autowired
	private ActivityDAO activityDAO;
	
	@Autowired
	private SolrServer solrServer;

	public List<Activity> listAllActivities() {
		return activityDAO.listAllActivities();
	}

	public List<Activity> listLatestActivities(Long afterId) {
		return activityDAO.listLatestActivities(afterId);
	}

	public List<Activity> listRangeActivities(Long startId, Long endId) {
		return activityDAO.listRangeActivities(startId, endId);
	}
	
	public Long addActivity(Activity activity) {
		Long id = activityDAO.addActivity(activity);
		activity.setId(id);
		
		logger.debug(MessageFormatter.format("Adding activity to Solr - {}", activity));
		try {
			solrServer.addBean(activity);
			solrServer.commit();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (SolrServerException e) {
			logger.error(e.getMessage());
		}
		
		return id;
		
	}	
	
	public List<Activity> findActivities(String pattern) {
		logger.debug(MessageFormatter.format("Serching for activities via Solr. Search pattern - {}", pattern));
		SolrQuery query = new SolrQuery();
		query.setQuery(String.format("text:%s", pattern));
		QueryResponse qr = null;
		List<Activity> activities = null;
		try {
			qr = solrServer.query(query);
			activities = qr.getBeans(Activity.class);
			
			//Retrieving activity data
			for (int i = 0; i < activities.size(); i++) {
				activities.set(i, activityDAO.getActivityById(activities.get(i).getId()));
			}
		} catch (SolrServerException e) {
			logger.error(e.getMessage());
		}
		return activities;
		
		//return activityDAO.findActivities(pattern);
	}
	
	@Override
	public void replicateActivitiesToSolr() throws IOException, SolrServerException {
		logger.debug("Started activities replication to SOLR");
		List<Activity> activities = activityDAO.listAllActivities();
		
		for (Activity activity : activities) {
			solrServer.addBean(activity);
		}
		solrServer.commit();
		logger.debug("Finished activities replication to SOLR");
	}
}
