package com.cab.allocation.admin.model;

import java.util.List;

public class Cabs {

	private List<Cab> cabs;
	
	

	public Cabs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cabs(List<Cab> cabs) {
		super();
		this.cabs = cabs;
	}

	public List<Cab> getCabs() {
		return cabs;
	}

	public void setCabs(List<Cab> cabs) {
		this.cabs = cabs;
	}	
}
