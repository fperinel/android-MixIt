package com.level42.mixtit.webservices;

import java.util.List;

import com.level42.mixtit.exceptions.CommunicationException;
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
	 */
	public Talk getTalk(Integer id) throws CommunicationException;

	/**
	 * Retourne le détail d'un LightningTalk
	 * 
	 * @return LightningTalk
	 * 
	 * @throws CommunicationException
	 */
	public LightningTalk getLightningTalk(Integer id) throws CommunicationException;
	
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
	public List<Staff> getStaff() throws CommunicationException;
	
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
	 */
	public Member getMember(Integer id) throws CommunicationException;
}
