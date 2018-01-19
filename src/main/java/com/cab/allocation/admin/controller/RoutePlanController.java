package com.cab.allocation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.allocation.admin.model.RoutePlan;
import com.cab.allocation.admin.service.RouteGraph;
import com.cab.allocation.admin.service.ShortestPath;

@RestController
public class RoutePlanController {

	private RouteGraph routeGraph;
	private ShortestPath shortestPath;

	@Autowired
	public RoutePlanController(RouteGraph routeGraph, ShortestPath shortestPath) {
		this.routeGraph = routeGraph;
		this.shortestPath = shortestPath;
	}

	@GetMapping("/routePlan")
	public RoutePlan getRoutePlan() {
		
		RoutePlan routePlan = routeGraph.getRoutePlan();

		routeGraph.getRoutePlan();
		
		return shortestPath.get(routePlan);

	}
}
