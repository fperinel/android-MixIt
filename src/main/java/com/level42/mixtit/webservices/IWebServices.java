package com.level42.mixtit.webservices;

import java.util.List;

import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.models.Interest;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.models.Member;
import com.level42.mixtit.models.Speaker;
import com.level42.mixtit.models.Sponsor;
import com.level42.mixtit.models.Staff;
import com.level42.mixtit.models.Talk;

/**
 * Interface des Webservices MixIt
 */
public interface IWebServices {

	/**
	 * Retourne la liste des talks
	 * 
	 * @return Liste des talks
	 * 
	 * @throws CommunicationException
	 */
	public List<Talk> getTalks() throws CommunicationException;

	/**
	 * Retourne la liste des lightning talks
	 * 
	 * @return Liste des lightning talks
	 * 
	 * @throws CommunicationException
	 */
	public List<LightningTalk> getLightningTalks() throws CommunicationException;

	/**
	 * Retourne le détail d'un talk
	 * 
	 * @return Talk
	 * 
	 * @throws CommunicationException
	 * @throws NotFoundException
	 */
	public Talk getTalk(Integer id) throws CommunicationException, NotFoundException;

	/**
	 * Retourne le détail d'un LightningTalk
	 * 
	 * @return LightningTalk
	 * 
	 * @throws CommunicationException
	 * @throws NotFoundException
	 */
	public LightningTalk getLightningTalk(Integer id) throws CommunicationException, NotFoundException;
	
	/**
	 * Retourne la liste des membres
	 * 
	 * @return Liste des membres
	 * 
	 * @throws CommunicationException
	 */
	public List<Member> getMembers() throws CommunicationException;
	
	/**
	 * Retourne la liste du staff
	 * 
	 * @return Liste du staff
	 * 
	 * @throws CommunicationException
	 */
	public List<Staff> getStaffs() throws CommunicationException;
	
	/**
	 * Retourne la liste des speakers
	 * 
	 * @return Liste des speakers
	 * 
	 * @throws CommunicationException
	 */
	public List<Speaker> getSpeakers() throws CommunicationException;
	
	/**
	 * Retourne la liste des sponsors
	 * 
	 * @return Liste des sponsors
	 * 
	 * @throws CommunicationException
	 */
	public List<Sponsor> getSponsors() throws CommunicationException;
	
	/**
	 * Retourne le détail d'un membre
	 * 
	 * @param id Identifiant du membre
	 * 
	 * @return Détail d'un membre
	 * 
	 * @throws CommunicationException
	 * @throws NotFoundException
	 */
	public Member getMember(Integer id) throws CommunicationException, NotFoundException;

	/**
	 * Retourne la liste des tags "intérêt"
	 * 
	 * @return Liste des tags "intérêt"
	 * 
	 * @throws CommunicationException
	 */
	public List<Interest> getInterests() throws CommunicationException;
	
	/**
	 * Retourne le détail d'un tags "intérêt"
	 * 
	 * @param id Identifiant du tags "intérêt"
	 * 
	 * @return Détail d'un tags "intérêt"
	 * 
	 * @throws CommunicationException
	 * @throws NotFoundException
	 */
	public Interest getInterest(Integer id) throws CommunicationException, NotFoundException;
}
