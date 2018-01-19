package com.cab.allocation.admin.model;

import java.util.List;

public class RoutePlan {

	private Double totalCost;
	private List<Route> routes;

	public RoutePlan() {
		super();
	}

	public RoutePlan(Double totalCost, List<Route> routes) {
		super();
		this.totalCost = totalCost;
		this.routes = routes;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

}
