package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Talk;

/**
 * Interface de manipulation des Talks de MixIT.
 */
public interface ITalkService {

    /**
     * Méthode listant l'ensemble des talks disponibles.
     *
     * @return Liste des talks
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    List<Talk> getTalks() throws FunctionnalException, TechnicalException;

    /**
     * Méthode retournant le détail d'un talk à partir de son identifiant.
     *
     * @param id
     *            Identifiant du talk
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    Talk getTalk(Integer id) throws FunctionnalException, TechnicalException;
    
    /**
     * Méthode listant les talks favoris d'un membre
     *
     * @return Liste des talks favoris
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    List<Talk> getFavorite(Integer id) throws FunctionnalException, TechnicalException;
}
