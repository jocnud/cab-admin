package com.cab.allocation.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.psjava.ds.graph.MutableDirectedWeightedGraph;
import org.psjava.ds.numbersystrem.IntegerNumberSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.allocation.admin.model.DropPoints;
import com.cab.allocation.admin.repository.DropPointsRepository;

@Service
public class RouteGraph {

	private DropPointsRepository dropPointsRepository;

	@Autowired
	public RouteGraph(DropPointsRepository dropPointsRepository) {
		this.dropPointsRepository = dropPointsRepository;
	}
	
//	public getRoutePlan(){
//		
//		//getRouteGraph
//		//getAllCabs
//		//getAllUsers
//	}

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

}
