package com.cab.allocation.admin.service;

public class Node {

	private String name;
	private String destination;
	private Integer distance;

	public Node(String name, String destination, Integer distance) {
		super();
		this.name = name;
		this.destination = destination;
		this.distance = distance;
	}

	public Node(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", destination=" + destination + ", distance=" + distance + "]";
	}
	
	

}
