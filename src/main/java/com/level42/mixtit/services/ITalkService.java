package com.level42.mixtit.services;

import java.util.List;

import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.models.Talk;

/**
 * Interface de manipulation des Talks de MixIT
 */
public interface ITalkService {

	/**
	 * Méthode listant l'ensemble des talks disponibles
	 * 
	 * @return Liste des talks
	 * 
	 * @throws CommunicationException En cas d'erreur de communication
	 */
	public List<Talk> getTalks() throws CommunicationException;
	
	/**
	 * Méthode retournant le détail d'un talk à partir de son 
	 * identifiant
	 * 
	 * @param id Identifiant du talk
	 * 
	 * @throws CommunicationException En cas d'erreur de communication
	 * @throws NotFoundException      Si aucun talk ne correspond à l'identifiant
	 */
	public Talk getTalk(Integer id) throws CommunicationException, NotFoundException;
}
