package com.cab.allocation.admin.model;

public class RouteDetail {

	private String teamMembers;
	private String routePlan;
	private Double routeCos;
	
	
	public String getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}
	public String getRoutePlan() {
		return routePlan;
	}
	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}
	public Double getRouteCos() {
		return routeCos;
	}
	public void setRouteCos(Double routeCos) {
		this.routeCos = routeCos;
	}
	@Override
	public String toString() {
		return "RouteDetail [teamMembers=" + teamMembers + ", routePlan=" + routePlan + ", routeCos=" + routeCos + "]";
	}
	
	
	
}
