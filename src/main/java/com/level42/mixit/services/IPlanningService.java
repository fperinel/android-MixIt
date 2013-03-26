package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.Session;

/**
 * Interface de manipulation des planning de MixIT
 */
public interface IPlanningService {

	/**
	 * Méthode retournant la date, la salle d'une session de talk
	 * 
	 * @return Date et salle d'une session de talk
	 * 
	 * @throws TechnicalException
	 * @throws FunctionnalException
	 */
	public Session getPlanningSession(Integer sessionId) throws FunctionnalException, TechnicalException;
}
