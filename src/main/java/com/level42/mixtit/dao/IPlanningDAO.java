package com.level42.mixtit.dao;

import com.level42.mixtit.exceptions.DataAccessException;
import com.level42.mixtit.models.Planning;

/**
 * Interface de manipulation des Talks de MixIT
 */
public interface IPlanningDAO {

	/**
	 * Méthode retournant le planning des sessions
	 * 
	 * @return Planning
	 * 
	 * @throws DataAccessException 
	 */
	public Planning getPlanning() throws DataAccessException;
}
