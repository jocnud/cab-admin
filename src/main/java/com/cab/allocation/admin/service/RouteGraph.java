package com.cab.allocation.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.psjava.ds.graph.MutableDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.allocation.admin.TeamMemberDTO;
import com.cab.allocation.admin.config.RestUtils;
import com.cab.allocation.admin.model.Cab;
import com.cab.allocation.admin.model.DropPoints;
import com.cab.allocation.admin.model.Route;
import com.cab.allocation.admin.model.RouteDetail;
import com.cab.allocation.admin.model.RoutePlan;
import com.cab.allocation.admin.repository.CabsRepository;
import com.cab.allocation.admin.repository.DropPointsRepository;

@Service
public class RouteGraph {

	private DropPointsRepository dropPointsRepository;
	private CabsRepository cabsRepository;
	private RestUtils restUtils;

	@Autowired
	public RouteGraph(DropPointsRepository dropPointsRepository, CabsRepository cabsRepository, RestUtils restUtils) {
		super();
		this.dropPointsRepository = dropPointsRepository;
		this.cabsRepository = cabsRepository;
		this.restUtils = restUtils;
	}

	public RoutePlan getRoutePlan() {
		List<Cab> cabs = cabsRepository.findAll();
		DropPoints dropPoints = dropPointsRepository.findAll().get(0);
		Map<String, List<TeamMemberDTO>> teamMembers = getAllTeamMemberByDropPoint(dropPoints);

		List<Route> persistRoute = new ArrayList<>();

		allocateWhenSameRouteSameCapacity(cabs, teamMembers, persistRoute);
		allocateWhenSameRouteCapacityLess(cabs, teamMembers, persistRoute);
		allocateWhenSameRouteCapacityMore(cabs, teamMembers, persistRoute);

		allocateLeftOvers(cabs, teamMembers, persistRoute);

		Map<String, RouteDetail> routeDetailMap = getRouteDetailedMap(persistRoute);
		System.out.println(routeDetailMap);
		RoutePlan routePlan = new RoutePlan();
		routePlan.setRoutePlan(routeDetailMap);

		return routePlan;

	}

	private Map<String, RouteDetail> getRouteDetailedMap(List<Route> persistRoute) {
		Map<String, RouteDetail> routeDetailMap = new HashMap<>();

		for (Route route : persistRoute) {
			if (routeDetailMap.containsKey(route.getCabId())) {
				RouteDetail routeDetail = routeDetailMap.get(route.getCabId());
				routeDetail.setRoutePlan(routeDetail.getRoutePlan() + "," + route.getRoute());
				routeDetail.setTeamMembers(routeDetail.getTeamMembers() + "," + route.getTeamMembers());
			} else {
				RouteDetail routeDetail = new RouteDetail();
				routeDetail.setRoutePlan(route.getRoute());
				routeDetail.setTeamMembers(route.getTeamMembers());
				routeDetailMap.put(route.getCabId(), routeDetail);
			}
		}
		return routeDetailMap;
	}

	private void allocateLeftOvers(List<Cab> cabs, Map<String, List<TeamMemberDTO>> teamMembers,
			List<Route> persistRoute) {
		List<TeamMemberDTO> leftOvers = getLeftOvers(teamMembers);

		ListIterator<TeamMemberDTO> iter = leftOvers.listIterator();
		while (iter.hasNext()) {
			TeamMemberDTO leftTeamMemeber = iter.next();
			if (leftTeamMemeber.isAllocated() != true) {
				for (Cab cab : cabs) {
					if (cabHasSpace(cab)) {
						sameCapacityAdd(cab, leftOvers, persistRoute);
						lessCapacityAdd(cab, leftOvers, persistRoute);
						moreCapacityAdd(cab, leftOvers, persistRoute);
					}
				}
				iter.remove();
			}
		}
	}

	private void allocateWhenSameRouteSameCapacity(List<Cab> cabs, Map<String, List<TeamMemberDTO>> teamMembers,
			List<Route> persistRoute) {
		for (Cab cab : cabs) {
			if (cabHasSpace(cab)) {
				Iterator<String> itr = teamMembers.keySet().iterator();
				while (itr.hasNext()) {
					String dropPoint = itr.next();
					List<TeamMemberDTO> teamMembersByGroup = teamMembers.get(dropPoint);
					sameCapacityAdd(cab, teamMembersByGroup, persistRoute);
				}

			}

		}
	}

	private void sameCapacityAdd(Cab cab, List<TeamMemberDTO> teamMembersByGroup, List<Route> persistRoute) {
		if ((!teamMembersByGroup.isEmpty()) && (teamMembersByGroup.size() == cab.getCapacity())) {

			teamMembersByGroup.stream().filter(t -> !t.isAllocated()).forEach(tm -> {
				if (cabHasSpace(cab)) {
					tm.setAllocated(true);
					cab.setAllocatedUsers(cab.getAllocatedUsers().intValue() + 1);
					System.out
							.println(String.format(" Allocated %s users to cabId %s ", tm.toString(), cab.getCabId()));

					addRouteToPersistList(cab, persistRoute, tm);

				} else {
					System.out.println("No space left");
				}

			});

		}
	}

	private void addRouteToPersistList(Cab cab, List<Route> persistRoute, TeamMemberDTO tm) {
		StringBuilder teamMemberBuilder = new StringBuilder();
		StringBuilder dropPointBuilder = new StringBuilder();
		Route route = new Route();
		teamMemberBuilder.append(tm.getTeamMemberId());

		dropPointBuilder.append(tm.getDropPoint());
		route.setRoute(dropPointBuilder.toString());
		route.setTeamMembers(teamMemberBuilder.toString());
		route.setCabId(cab.getCabId());

		persistRoute.add(route);
	}

	private void allocateWhenSameRouteCapacityMore(List<Cab> cabs, Map<String, List<TeamMemberDTO>> teamMembers,
			List<Route> persistRoute) {
		for (Cab cab : cabs) {

			if (cabHasSpace(cab)) {

				Iterator<String> itr = teamMembers.keySet().iterator();
				while (itr.hasNext()) {

					String dropPoint = itr.next();

					List<TeamMemberDTO> teamMembersByGroup = teamMembers.get(dropPoint).stream()
							.filter(t -> !t.isAllocated()).collect(Collectors.toList());

					moreCapacityAdd(cab, teamMembersByGroup, persistRoute);

				}

			}

		}
	}

	private void moreCapacityAdd(Cab cab, List<TeamMemberDTO> teamMembersByGroup, List<Route> persistRoute) {
		if ((!teamMembersByGroup.isEmpty()) && (teamMembersByGroup.size() > cab.getCapacity())) {

			teamMembersByGroup.stream().filter(t -> !t.isAllocated()).forEach(tm -> {
				if (cabHasSpace(cab)) {
					tm.setAllocated(true);
					cab.setAllocatedUsers(cab.getAllocatedUsers().intValue() + 1);
					System.out
							.println(String.format(" Allocated %s users to cabId %s ", tm.toString(), cab.getCabId()));
					addRouteToPersistList(cab, persistRoute, tm);

				} else {
					System.out.println("No space left");
				}

			});

		}
	}

	private void allocateWhenSameRouteCapacityLess(List<Cab> cabs, Map<String, List<TeamMemberDTO>> teamMembers,
			List<Route> persistRoute) {
		for (Cab cab : cabs) {

			if (cabHasSpace(cab)) {

				Iterator<String> itr = teamMembers.keySet().iterator();
				while (itr.hasNext()) {

					String dropPoint = itr.next();
					List<TeamMemberDTO> teamMembersByGroup = teamMembers.get(dropPoint).stream()
							.filter(t -> !t.isAllocated()).collect(Collectors.toList());

					lessCapacityAdd(cab, teamMembersByGroup, persistRoute);

				}

			}

		}
	}

	private void lessCapacityAdd(Cab cab, List<TeamMemberDTO> teamMembersByGroup, List<Route> persistRoute) {
		if ((!teamMembersByGroup.isEmpty()) && (teamMembersByGroup.size() < cab.getCapacity())) {

			teamMembersByGroup.stream().filter(t -> !t.isAllocated()).forEach(tm -> {
				if (cabHasSpace(cab)) {
					tm.setAllocated(true);
					cab.setAllocatedUsers(cab.getAllocatedUsers().intValue() + 1);
					System.out
							.println(String.format(" Allocated %s users to cabId %s ", tm.toString(), cab.getCabId()));

					addRouteToPersistList(cab, persistRoute, tm);

				} else {
					System.out.println("No space left");
				}

			});

		}
	}

	private boolean cabHasSpace(Cab cab) {
		return cab.getAllocatedUsers() < cab.getCapacity();
	}

	private Map<String, List<TeamMemberDTO>> getAllTeamMemberByDropPoint(DropPoints dropPoints) {
		List<TeamMemberDTO> teamMembers = restUtils.getAllTeamMembers();
		Map<String, List<TeamMemberDTO>> groupedDropPointTeamMembers = new HashMap<>();
		Iterator<String> itr = dropPoints.getRoutes().keySet().iterator();
		while (itr.hasNext()) {

			String dropPoint = itr.next();
			groupedDropPointTeamMembers.put(dropPoint, teamMembers.stream()
					.filter(teamMember -> teamMember.getDropPoint().equals(dropPoint)).collect(Collectors.toList()));
		}
		return groupedDropPointTeamMembers;
	}

	private List<TeamMemberDTO> getLeftOvers(Map<String, List<TeamMemberDTO>> teamMembers) {
		List<TeamMemberDTO> leftOvers = new ArrayList<>();
		Iterator<String> itr = teamMembers.keySet().iterator();

		while (itr.hasNext()) {

			String dropPoint = itr.next();
			List<TeamMemberDTO> teamMembersByGroup = teamMembers.get(dropPoint).stream().filter(t -> !t.isAllocated())
					.collect(Collectors.toList());

			leftOvers.addAll(teamMembersByGroup);
		}
		return leftOvers;
	}

}
