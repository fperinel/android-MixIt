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
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des membres.
 */
public class EntityService extends AbstractService implements IEntityService {

    /**
     * Interface vers les webservices.
     */
    @Inject
    private IWebServices ws;

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IEntityService#getMembers()
     */
    public List<Member> getMembers() throws FunctionnalException,
            TechnicalException {
        try {
            return ws.getMembers();
        } catch (CommunicationException e) {
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
            return ws.getSponsors();
        } catch (CommunicationException e) {
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
            return ws.getSpeakers();
        } catch (CommunicationException e) {
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
            return ws.getStaffs();
        } catch (CommunicationException e) {
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
     * Interroge le ws pour retourner une entité (membr, staff ...).
     * @param <T> Type d'entité (membre, speaker, sponsor ...)
     * @param id Identifiant de l'entité
     * @return Entité
     */
    protected <T> Object getEntity(Integer id, T type)
            throws FunctionnalException, TechnicalException {
        try {
            return (T) ws.getEntity(id, type);
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        } catch (NotFoundException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_NotFoundException), e);
        }
    }

    @Override
    public Member getMemberIdByLogin(String login) throws FunctionnalException,
            TechnicalException {
        List<Member> members = this.getMembers();
        for (Member member : members) {
            if (member.getLogin().equals(login)) {
                return member;
            }
        }
        return null;
    }

}
