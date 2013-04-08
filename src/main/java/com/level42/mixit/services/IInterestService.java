package com.level42.mixit.services;

import java.util.List;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Interest;

/**
 * Interface de manipulation des centre d'intérêt de MixIT.
 */
public interface IInterestService {

    /**
     * Méthode listant l'ensemble des tags.
     * @return Liste des tags
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public List<Interest> getInterests() throws FunctionnalException,
            TechnicalException;

    /**
     * Méthode retournant le détail d'un tag.
     * @param id Identifiant du tag
     * @throws TechnicalException
     * @throws FunctionnalException
     */
    public Interest getInterest(Integer id) throws FunctionnalException,
            TechnicalException;
}
