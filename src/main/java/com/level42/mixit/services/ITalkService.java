package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.Talk;

/**
 * Interface de manipulation des Talks de MixIT
 */
public interface ITalkService {

	/**
	 * Méthode listant l'ensemble des talks disponibles
	 * 
	 * @return Liste des talks
	 * 
	 * @throws TechnicalException
	 * @throws FunctionnalException
	 */
	public List<Talk> getTalks() throws FunctionnalException, TechnicalException;
	
	/**
	 * Méthode retournant le détail d'un talk à partir de son 
	 * identifiant
	 * 
	 * @param id Identifiant du talk
	 * 
	 * @throws TechnicalException
	 * @throws FunctionnalException
	 */
	public Talk getTalk(Integer id) throws FunctionnalException, TechnicalException;
	
	/**
	 * Méthode retournant les talks groupés par date
	 * 
	 * @return Talk groupés par date
	 * 
	 * @throws TechnicalException
	 * @throws FunctionnalException
	 */
	public List<GroupedTalks> getTalksForPlanning() throws FunctionnalException, TechnicalException;
}
