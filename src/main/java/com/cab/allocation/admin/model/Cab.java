package com.cab.allocation.admin.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Cab {

	@Id
	private String id;
	@NotNull
	@Indexed(unique=true)
	private String cabId;
	@NotNull
	private Double cost;
	@NotNull
	private Integer capacity;
	
	@Transient
	private Integer allocatedUsers=0;
	
	public Integer getAllocatedUsers() {
		return allocatedUsers;
	}

	public void setAllocatedUsers(Integer allocatedUsers) {
		this.allocatedUsers = allocatedUsers;
	}

	public Cab() {
	}
	
	public Cab(String cabId, Double cost, Integer capacity) {
		super();
		this.cabId = cabId;
		this.cost = cost;
		this.capacity = capacity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCabId() {
		return cabId;
	}
	public void setCabId(String cabId) {
		this.cabId = cabId;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	

}
