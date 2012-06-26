package com.tvelykyy.afeeder.domain;

public class JsonResponse {
	private final String SUCCESS = "SUCCESS";
	private final String FAIL = "FAIL";
	private String status = null;
	private Object result = null;
	
	public String getStatus() {
        return status;
	}
	public void setStatus(String status) {
        this.status = status;
	}
	public Object getResult() {
        return result;
	}
	public void setResult(Object result) {
        this.result = result;
	}
	
	public void setSuccess(Object obj) {
		setStatus(SUCCESS);
		setResult(obj);
	}
	
	public void setFail(Object obj) {
		setStatus(FAIL);
		setResult(obj);
	}
}

