package com.cab.allocation.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.allocation.admin.config.RestUtils;

@Service
public class ShortestPath {

	private RestUtils restUtils;
	private RouteGraph routeGraph;

	@Autowired
	public ShortestPath(RestUtils restUtils, RouteGraph routeGraph) {
		this.restUtils = restUtils;
		this.routeGraph = routeGraph;
	}

	public List getAllTeamMembers() {
		
		routeGraph.getGraph();
		return restUtils.getAllTeamMembers();
	}

}
