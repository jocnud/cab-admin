package com.cab.allocation.admin;

public class TeamMemberDTO {
	private String id;
	private String teamMemberId;
	private Gender gender;
	private String dropPoint;
	private boolean isAllocated;
	
	public boolean isAllocated() {
		return isAllocated;
	}

	public void setAllocated(boolean isAllocated) {
		this.isAllocated = isAllocated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(String teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getDropPoint() {
		return dropPoint;
	}

	public void setDropPoint(String dropPoint) {
		this.dropPoint = dropPoint;
	}

	@Override
	public String toString() {
		return "TeamMemberDTO [id=" + teamMemberId + ", gender=" + gender + ", drop=" + dropPoint
				+ ", done=" + isAllocated + "]";
	}



}

enum Gender {
	M, F
}
