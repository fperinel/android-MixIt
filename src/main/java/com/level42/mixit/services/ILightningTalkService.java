package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.LightningTalk;

/**
 * Interface de manipulation des lightning Talks de MixIT.
 */
public interface ILightningTalkService {

    /**
     * Méthode listant l'ensemble des talks disponibles.
     * @return Liste des talks
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<LightningTalk> getLightningTalks() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un talk à partir de son identifiant.
     * @param id Identifiant du talk
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public LightningTalk getLightningTalk(Integer id)
            throws FunctionnalException, TechnicalException;
}
