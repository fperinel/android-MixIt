package com.level42.mixit.services.impl;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Interest;
import com.level42.mixit.services.IInterestService;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des centre d'intérêts.
 */
public class InterestService extends AbstractService implements
        IInterestService {

    /**
     * Interface vers les webservices.
     */
    @Inject
    private IWebServices ws;

    /**
     * Retourne l'interface des webservices.
     * @return the ws
     */
    public IWebServices getWs() {
        return ws;
    }
    
    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IInterestService#getInterests()
     */
    public List<Interest> getInterests() throws FunctionnalException,
            TechnicalException {
        try {
            return ws.getInterests();
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.IInterestService#getInterest(java.lang.Integer)
     */
    public Interest getInterest(Integer id) throws FunctionnalException,
            TechnicalException {
        try {
            return ws.getInterest(id);
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        } catch (NotFoundException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_NotFoundException), e);
        }
    }
}
