package com.level42.mixit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Interest;
import com.level42.mixit.models.Session;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.services.IInterestService;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des Talks.
 */
public class TalkService extends AbstractService implements ITalkService {
    
    /**
     * Interface vers les webservices.
     */
    @Inject
    private IWebServices ws;

    /**
     * Interface vers le service de gestion des plannings.
     */
    @Inject
    private IPlanningService planningService;
    
    /**
     * Interface vers le service de gestion des entités.
     */
    @Inject
    private IEntityService entityService;

    /**
     * Interface vers le service de gestion des centres d'intérêts.
     */
    @Inject
    private IInterestService interestService;

    /**
     * Liste des speakers indéxés par identifiant.
     */
    private Map<Integer, Speaker> idxSpeakers;

    /**
     * Interface vers le service de gestion des centres d'intérêts.
     */
    private Map<Integer, Interest> idxInterests;

    /**
     * Retourne l'interface Webservice
     * @return the ws
     */
    public IWebServices getWs() {
        return ws;
    }

    /**
     * Reoturne le service de gestion du planning
     * @return the planningService
     */
    public IPlanningService getPlanningService() {
        return planningService;
    }

    /**
     * Retourne le service de gestion des entités
     * @return the entityService
     */
    public IEntityService getEntityService() {
        return entityService;
    }

    /**
     * Retourne le service de gestion des centres d'intérêts
     * @return the interestService
     */
    public IInterestService getInterestService() {
        return interestService;
    }

    /**
     * Retourne la liste indexées des speakers
     * @return the speakers
     */
    public Map<Integer, Speaker> getIdxSpeakers() {
        return idxSpeakers;
    }

    /**
     * Retourne la liste indexées des centre d'intérêts
     * @return the interests
     */
    public Map<Integer, Interest> getIdxInterests() {
        return idxInterests;
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.ITalkService#getTalks()
     */
    public List<Talk> getTalks() throws FunctionnalException,
            TechnicalException {
        try {
            return ws.getTalks();
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.ITalkService#getTalk(java.lang.Integer)
     */
    public Talk getTalk(Integer id) throws FunctionnalException,
            TechnicalException {
        try {
            Talk talk = ws.getTalk(id);

            // Dans la nouvelle version des APIP, les objects sont retournés
            this.hydrateTalkInterests(talk);
            this.hydrateTalkSession(talk);
            this.hydrateTalkSpeakers(talk);

            return talk;
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        } catch (NotFoundException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_NotFoundException), e);
        }
    }

    /**
     * Hydrate la session d'un talk.
     * @param talk
     *            Talk à hydrater
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected void hydrateTalkSession(Talk talk) throws FunctionnalException,
            TechnicalException {
        // Ajoute le planning
        Session session = planningService.getPlanningSession(talk.getId());
        talk.setSession(session);
    }

    /**
     * Hydrate les speakers d'un talk.
     * @param talk
     *            Talk à hydrater
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected void hydrateTalkSpeakers(Talk talk) throws FunctionnalException,
            TechnicalException {
        if (talk.getSpeakersId() != null) {
            List<Speaker> speakers = new ArrayList<Speaker>();
            for (Integer id : talk.getSpeakersId()) {
                Speaker spk = this.getSpeakerById(id);
                if (spk != null) {
                    Bitmap image = Utils.loadBitmap(spk.getUrlimage());
                    spk.setImage(image);
                    speakers.add(spk);
                }
            }
            talk.setSpeakers(speakers);
        }
    }

    /**
     * Hydrate les centres d'intérêt d'un talk.
     * @param talk
     *            Talk à hydrater
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected void hydrateTalkInterests(Talk talk) throws FunctionnalException,
            TechnicalException {
        if (talk.getInterestsId() != null) {
            List<Interest> interests = new ArrayList<Interest>();
            for (Integer id : talk.getInterestsId()) {
                Interest interest = this.getInterestById(id);
                if (interest != null) {
                    interests.add(interest);
                }
            }
            talk.setInterests(interests);
        }
    }

    /**
     * Retourne un objet speaker par son identifiant.
     * @param id Identifiant du speaker
     * @return Speaker
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected Speaker getSpeakerById(Integer id) throws FunctionnalException,
            TechnicalException {
        if (idxSpeakers == null) {
            idxSpeakers = new HashMap<Integer, Speaker>();
            // Ajoute les speakers
            List<Speaker> allSpeakers = entityService.getSpeakers();
            for (Speaker spk : allSpeakers) {
                idxSpeakers.put(spk.getId(), spk);
            }
        }
        return idxSpeakers.get(id);
    }

    /**
     * Retourne un objet centre d'intérêt par son identifiant.
     * @param id Identifiant du centre d'intérêt
     * @return Centre d'intérêt 
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected Interest getInterestById(Integer id) throws FunctionnalException,
            TechnicalException {
        if (idxInterests == null) {
            idxInterests = new HashMap<Integer, Interest>();
            // Ajoute les speakers
            List<Interest> allInterests = interestService.getInterests();
            for (Interest interest : allInterests) {
                idxInterests.put(interest.getId(), interest);
            }
        }
        return idxInterests.get(id);
    }

}
