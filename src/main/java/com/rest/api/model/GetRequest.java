package com.rest.api.model;

public class GetRequest {
	String tcID;
	String priority;
	String description;
	String webURL;
	String serviceURL;
	String customerId;
	
	public GetRequest() {
		
	}
	
	public GetRequest(String tcID, String description, String webURL, String serviceURL, String customerId) {
		this.tcID = tcID;
		this.description = description;
		this.webURL=webURL;
		this.serviceURL=serviceURL;
		this.customerId=customerId;
	}

	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTcID() {
		return tcID;
	}

	public void setTcID(String tcID) {
		this.tcID = tcID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebURL() {
		return webURL;
	}

	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	
	
}
