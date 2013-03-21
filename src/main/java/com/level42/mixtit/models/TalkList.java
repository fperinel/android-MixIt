package com.level42.mixtit.models;

import java.util.List;
import java.util.Observable;

/**
 * Objet représentant une liste de talk
 *  Permet d'ajouter un observer
 */
public class TalkList extends Observable {

	private List<Talk> talks;

	/**
	 * @return the talks
	 */
	public List<Talk> getTalks() {
		return talks;
	}

	/**
	 * @param talks the talks to set
	 */
	public void setTalks(List<Talk> talks) {
		this.talks = talks;
		setChanged();
		notifyObservers(this);
	}
	
}
