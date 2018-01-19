package com.cab.allocation.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.psjava.algo.graph.shortestpath.DijkstraAlgorithm;
import org.psjava.algo.graph.shortestpath.SingleSourceShortestPathResult;
import org.psjava.ds.graph.DirectedWeightedEdge;
import org.psjava.ds.graph.MutableDirectedWeightedGraph;
import org.psjava.ds.numbersystrem.IntegerNumberSystem;
import org.psjava.goods.GoodDijkstraAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.allocation.admin.config.RestUtils;
import com.cab.allocation.admin.model.DropPoints;
import com.cab.allocation.admin.model.RouteDetail;
import com.cab.allocation.admin.model.RoutePlan;
import com.cab.allocation.admin.repository.DropPointsRepository;

@Service
public class ShortestPath {

	private DropPointsRepository dropPointsRepository;

	@Autowired
	public ShortestPath(DropPointsRepository dropPointsRepository) {
		super();
		this.dropPointsRepository = dropPointsRepository;
	}

	public MutableDirectedWeightedGraph<String, Integer> getGraph() {
		MutableDirectedWeightedGraph<String, Integer> graph = MutableDirectedWeightedGraph.create();
		DropPoints dropPoints = dropPointsRepository.findAll().get(0);
		Map<String, String> routeMap = dropPoints.getRoutes();

		Set<String> keyNodes = routeMap.keySet();
		List<String> keyList = new ArrayList<>(keyNodes);
		System.out.println(keyList);

		Iterator<String> itr = keyNodes.iterator();

		while (itr.hasNext()) {
			String keyNodeName = itr.next();
			// add vertex
			graph.insertVertex(keyNodeName);

			String valueNode = routeMap.get(keyNodeName);

			List<String> valueNodes = Arrays.asList(valueNode.split(","));

			System.out.print(keyNodeName + " ");
			for (int i = 0; i < valueNodes.size(); i++) {
				System.out.print(String.format("{ %s %s }", valueNodes.get(i), keyList.get(i + 1)));
				graph.addEdge(keyNodeName, keyList.get(i + 1), new Integer(valueNodes.get(i)));
			}

			System.out.println("");
		}
		return graph;
	}

	public RoutePlan get(RoutePlan routePlan) {

		IntegerNumberSystem NS = IntegerNumberSystem.getInstance();

		DijkstraAlgorithm dijkstra = GoodDijkstraAlgorithm.getInstance();
		SingleSourceShortestPathResult<String, Integer, DirectedWeightedEdge<String, Integer>> graphRes = dijkstra
				.calc(getGraph(), "target_headquarter", NS);

		Map<String, RouteDetail> map = routePlan.getRoutePlan();
		Iterator<String> routeItr = map.keySet().iterator();

		while (routeItr.hasNext()) {
			String cabId = routeItr.next();

			String drops = map.get(cabId).getRoutePlan();

			List<String> dropsAsList = Arrays.asList(drops.split(","));

			if (dropsAsList.size() == 1) {
				graphRes.getDistance(dropsAsList.get(0));
				map.get(cabId).setRouteCos(map.get(cabId).getRouteCos());
			} else {
				
			}

		}

		return routePlan;
	}

}
