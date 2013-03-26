package com.level42.mixit.models;

import java.util.List;
import java.util.Observable;

public class PlanningTalk extends Observable {

	private List<GroupedTalks> groupedTalks;

	/**
	 * @return the groupedTalks
	 */
	public List<GroupedTalks> getGroupedTalks() {
		return groupedTalks;
	}

	/**
	 * @param groupedTalks the groupedTalks to set
	 */
	public void setGroupedTalks(List<GroupedTalks> groupedTalks) {
		this.groupedTalks = groupedTalks;
		setChanged();
		notifyObservers(this);
	}
	
}
