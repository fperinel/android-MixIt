package com.level42.mixit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.activities.PreferencesActivity;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Favoris;
import com.level42.mixit.models.Interest;
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
     * Liste des intérêts indéxés par identifiant.
     */
    private Map<Integer, Interest> idxInterests;

    /**
     * Liste des favoris indéxés par identifiant.
     */
    private Map<Integer, Favoris> idxFavoris;

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

    /**
     * Retourne la liste indexées des favoris
     * @return Favoris
     */
    public Map<Integer, Favoris> getIdxFavoris() {
        return idxFavoris;
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.ITalkService#getTalks()
     */
    @Override
    public List<Talk> getTalks() throws FunctionnalException,
            TechnicalException {
        try {
            List<Talk> talks = ws.getTalks();
            for (Talk talk : talks) {
                this.hydrateTalkFavoris(talk);
            }
            
            // On force le rechargement systématique
            idxFavoris = null;
            
            return talks;
        } catch (CommunicationException e) {
            throw new TechnicalException(
                    getText(R.string.exception_message_CommunicationException),
                    e);
        } catch (NotFoundException e) {
            throw new FunctionnalException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.ITalkService#getTalk(java.lang.Integer)
     */
    @Override
    public Talk getTalk(Integer id) throws FunctionnalException,
            TechnicalException {
        try {
            Talk talk = ws.getTalk(id);

            // Dans la nouvelle version des API, les objects sont retournés
            this.hydrateTalkInterests(talk);
            this.hydrateTalkSpeakers(talk);
            this.hydrateTalkFavoris(talk);

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

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.services.ITalkService#getFavorite(java.lang.Integer)
     */
    @Override
    public List<Favoris> getFavoris(String login) throws FunctionnalException,
            TechnicalException {
        try {
            return ws.getFavorite(login);
        } catch (CommunicationException e) {
            return null; // Dans le cas des favoris, pas d'impact sur le fonctionnement
        } catch (NotFoundException e) {
            return null; // Dans le cas des favoris, pas d'impact sur le fonctionnement
        }
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
     * Hydrate les status favoris d'un talk
     * @param talk
     *            Talk à hydrater
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected void hydrateTalkFavoris(Talk talk) throws FunctionnalException,
            TechnicalException {
        Favoris favoris = this.getFavorisById(talk.getId());
        if (favoris != null) {
            talk.setFavoris(true);
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

    /**
     * Retourne un objet favoris par son identifiant.
     * @param id Identifiant du favoris
     * @return Favoris
     * @throws FunctionnalException
     * @throws TechnicalException
     */
    protected Favoris getFavorisById(Integer id) throws FunctionnalException,
            TechnicalException {
        if (idxFavoris == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            String login = preferences.getString(PreferencesActivity.PREF_MEMBRE_LOGIN, null);
            if (login != null) {
                List<Favoris> favoris = this.getFavoris(login);
                idxFavoris = new HashMap<Integer, Favoris>();
                if (favoris != null) {
                    for (Favoris favori : favoris) {
                        idxFavoris.put(favori.getId(), favori);
                    }
                }
            } else {
                return null;
            }
        }
        return idxFavoris.get(id);
    }
}
