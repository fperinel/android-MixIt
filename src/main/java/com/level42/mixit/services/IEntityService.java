package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Member;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Sponsor;
import com.level42.mixit.models.Staff;

/**
 * Interface de manipulation des membres de MixIT
 */
public interface IEntityService {

    /**
     * Méthode listant l'ensemble des membres
     * 
     * @return Liste des membres
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<Member> getMembers() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode listant l'ensemble des sponsors
     * 
     * @return Liste des sponsors
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<Sponsor> getSponsors() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode listant l'ensemble des speakers
     * 
     * @return Liste des speakers
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<Speaker> getSpeakers() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode listant l'ensemble des membres du staff
     * 
     * @return Liste des membres du staff
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<Staff> getStaffs() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un membre
     * 
     * @param id
     *            Identifiant du membre
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public Member getMember(Integer id) throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un speaker
     * 
     * @param id
     *            Identifiant du speaker
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public Speaker getSpeaker(Integer id) throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un membre du staff
     * 
     * @param id
     *            Identifiant du membre du staff
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public Staff getStaff(Integer id) throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un sponsor
     * 
     * @param id
     *            Identifiant du sponsor
     * 
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public Sponsor getSponsor(Integer id) throws FunctionnalException,
            TechnicalException;
}
