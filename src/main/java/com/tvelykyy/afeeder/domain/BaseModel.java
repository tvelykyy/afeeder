package com.tvelykyy.afeeder.domain;

import org.apache.solr.client.solrj.beans.Field;

public abstract class BaseModel {
	@Field
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
