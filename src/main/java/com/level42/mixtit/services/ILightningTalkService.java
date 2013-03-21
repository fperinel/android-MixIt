package com.level42.mixtit.services;

import java.util.List;

import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.models.LightningTalk;

/**
 * Interface de manipulation des lightning Talks de MixIT
 */
public interface ILightningTalkService {

	/**
	 * Méthode listant l'ensemble des talks disponibles
	 * 
	 * @return Liste des talks
	 * 
	 * @throws CommunicationException En cas d'erreur de communication
	 */
	public List<LightningTalk> getLightningTalks() throws CommunicationException;
	
	/**
	 * Méthode retournant le détail d'un talk à partir de son 
	 * identifiant
	 * 
	 * @param id Identifiant du talk
	 * 
	 * @throws CommunicationException En cas d'erreur de communication
	 * @throws NotFoundException      Si aucun talk ne correspond à l'identifiant
	 */
	public LightningTalk getLightningTalk(Integer id) throws CommunicationException, NotFoundException;
}
