package com.cab.allocation.admin.model;

public class Route {

	private String cabId;
	private String teamMembers;
	private String route;
	private Double routeCost;

	public Route() {
	}

	public Route(String cabId, String teamMembers, String route, Double routeCost) {
		this.cabId = cabId;
		this.teamMembers = teamMembers;
		this.route = route;
		this.routeCost = routeCost;
	}

	public String getCabId() {
		return cabId;
	}

	public void setCabId(String cabId) {
		this.cabId = cabId;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Double getRouteCost() {
		return routeCost;
	}

	public void setRouteCost(Double routeCost) {
		this.routeCost = routeCost;
	}

	@Override
	public String toString() {
		return "Route [cabId=" + cabId + ", teamMembers=" + teamMembers + ", route=" + route + ", routeCost="
				+ routeCost + "]";
	}

}
