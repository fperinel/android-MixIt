package com.level42.mixit.dao;

import com.level42.mixit.exceptions.DataAccessException;
import com.level42.mixit.models.Planning;

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
