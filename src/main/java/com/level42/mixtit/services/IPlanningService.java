package com.level42.mixtit.services;

import com.level42.mixtit.models.Session;

/**
 * Interface de manipulation des planning de MixIT
 */
public interface IPlanningService {

	/**
	 * Méthode retournant la date, la salle d'une session de talk
	 * 
	 * @return Date et salle d'une session de talk
	 */
	public Session getPlanningSession(Integer sessionId);
}
