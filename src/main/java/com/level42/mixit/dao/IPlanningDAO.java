package com.level42.mixit.dao;

import com.level42.mixit.exceptions.DataAccessException;
import com.level42.mixit.models.Planning;

/**
 * Interface de manipulation des sessions de MixIT.
 */
public interface IPlanningDAO {

    /**
     * Méthode retournant le planning des sessions.
     *
     * @return Planning
     * @throws DataAccessException
     */
    Planning getPlanning() throws DataAccessException;
}
