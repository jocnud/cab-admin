package com.cab.allocation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.allocation.admin.config.RestUtils;
import com.cab.allocation.admin.model.RoutePlan;
import com.cab.allocation.admin.repository.CabsRepository;
import com.cab.allocation.admin.service.RouteGraph;
import com.cab.allocation.admin.service.ShortestPath;

@RestController
public class RoutePlanController {

	private RouteGraph routeGraph;
	private ShortestPath shortestPath;
	private RestUtils restUtils;
	private CabsRepository cabsRepository;

	@Autowired
	public RoutePlanController(RouteGraph routeGraph, ShortestPath shortestPath, RestUtils restUtils,
			CabsRepository cabsRepository) {
		this.routeGraph = routeGraph;
		this.shortestPath = shortestPath;
		this.restUtils = restUtils;
		this.cabsRepository = cabsRepository;
	}

	@GetMapping("/routePlan")
	public RoutePlan getRoutePlan() {

		long cabCapacityCount = cabsRepository.count();
		long allTeamMember = restUtils.getAllTeamMembers().size();

		if (cabCapacityCount < allTeamMember) {
			throw new RuntimeException(
					String.format("Please add more cabs teammembers are { %d} and total cab capacity is { %d }",
							allTeamMember, cabCapacityCount));
		}

		RoutePlan routePlan = routeGraph.getRoutePlan();

		routeGraph.getRoutePlan();

		return shortestPath.get(routePlan);

	}
}
