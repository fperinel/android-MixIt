package com.level42.mixit.services.impl;

import android.content.Context;

import com.google.inject.Inject;

/**
 * Service de base.
 */
public class AbstractService {

    /**
     * Contexte de l'application.
     */
    @Inject
    private Context context;

    /**
     * Retourne le contexte de l'application.
     * @return Contexte de l'application
     */
    public Context getContext() {
        return context;
    }

    /**
     * Méthode de traduction dans les servcies.
     * @param resId
     *            Identifiant de la ressources "String".
     * @return Valeur de la traduction
     */
    public String getText(Integer resId) {
        return getContext().getText(resId).toString();
    }

}
