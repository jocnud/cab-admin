package com.cab.allocation.admin.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DropPoints {

	@Id
	private String id;

	private Map<String, String> routes;

	public DropPoints() {

	}

	public DropPoints(Map<String, String> routes) {
		this.routes = routes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, String> routes) {
		this.routes = routes;
	}

}
