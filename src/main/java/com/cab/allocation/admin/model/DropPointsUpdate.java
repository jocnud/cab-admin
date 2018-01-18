package com.cab.allocation.admin.model;

import java.util.List;

public class DropPointsUpdate {

	private String id;
	
	private List<String> routes;

	public DropPointsUpdate() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}
	
	
}
