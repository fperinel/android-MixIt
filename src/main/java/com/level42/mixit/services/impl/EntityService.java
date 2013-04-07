package com.level42.mixit.services.impl;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Member;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Sponsor;
import com.level42.mixit.models.Staff;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des membres
 */
public class EntityService extends AbstractService implements IEntityService {

    /**
     * Interface vers les webservices
     */
    @Inject
    private IWebServices ws;

    /**
     * Interface vers le service des plannings
     */
    @Inject
    private IPlanningService planningService;

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getMembers()
     */
    public List<Member> getMembers() throws FunctionnalException,
            TechnicalException {
        try {
            List<Member> members = ws.getMembers();
            return members;
        } catch (CommunicationException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getSponsors()
     */
    public List<Sponsor> getSponsors() throws FunctionnalException,
            TechnicalException {
        try {
            List<Sponsor> sponsors = ws.getSponsors();
            return sponsors;
        } catch (CommunicationException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getSpeakers()
     */
    public List<Speaker> getSpeakers() throws FunctionnalException,
            TechnicalException {
        try {
            List<Speaker> speakers = ws.getSpeakers();
            return speakers;
        } catch (CommunicationException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getStaffs()
     */
    public List<Staff> getStaffs() throws FunctionnalException,
            TechnicalException {
        try {
            List<Staff> staffs = ws.getStaffs();
            return staffs;
        } catch (CommunicationException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getMember(java.lang.Integer)
     */
    public Member getMember(Integer id) throws FunctionnalException,
            TechnicalException {
        return (Member) this.getEntity(id, new Member());
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getSpeaker(java.lang.Integer)
     */
    public Speaker getSpeaker(Integer id) throws FunctionnalException,
            TechnicalException {
        return (Speaker) this.getEntity(id, new Speaker());
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getStaff(java.lang.Integer)
     */
    public Staff getStaff(Integer id) throws FunctionnalException,
            TechnicalException {
        return (Staff) this.getEntity(id, new Staff());
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getSponsor(java.lang.Integer)
     */
    public Sponsor getSponsor(Integer id) throws FunctionnalException,
            TechnicalException {
        return (Sponsor) this.getEntity(id, new Sponsor());
    }

    /**
     * Interroge le ws pour retourner une entité (membr, staff ...)
     * 
     * @param <T> Type d'entité (membre, speaker, sponsor ...)
     * 
     * @param id
     *            Identifiant de l'entité
     * @return Entité
     */
    protected <T> Object getEntity(Integer id, T type)
            throws FunctionnalException, TechnicalException {
        try {
            T entity = (T) ws.getEntity(id, type);
            return entity;
        } catch (CommunicationException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new TechnicalException(
                    getText(R.string.exception_message_NotFoundException), e);
        }
    }

}
