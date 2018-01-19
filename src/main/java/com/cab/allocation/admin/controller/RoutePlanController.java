package com.cab.allocation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab.allocation.admin.model.RoutePlan;
import com.cab.allocation.admin.service.ShortestPath;

@RestController
public class RoutePlanController {

	private ShortestPath shortestPath;

	@Autowired
	public RoutePlanController(ShortestPath shortestPath) {
		this.shortestPath = shortestPath;
	}

	@GetMapping("/routePlan")
	public RoutePlan getRoutePlan() {

		shortestPath.getAllTeamMembers();
		return new RoutePlan();

	}
}
