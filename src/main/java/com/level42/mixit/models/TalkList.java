package com.level42.mixit.models;

import java.util.List;
import java.util.Observable;

/**
 * Objet représentant une liste de talk Permet d'ajouter un observer.
 */
public class TalkList extends Observable {

    /**
     * Liste des talks.
     */
    private List<Talk> talks;

    /**
     * Retourne la liste des talks.
     * 
     * @return the talks
     */
    public List<Talk> getTalks() {
        return talks;
    }

    /**
     * Renseigne la liste des talks.
     * 
     * @param talks
     *            the talks to set
     */
    public void setTalks(List<Talk> talks) {
        this.talks = talks;
        setChanged();
        notifyObservers(this);
    }

}
