package com.level42.mixit.models;

import java.util.List;
import java.util.Observable;

/**
 * Objet représentant le planning des sessiosn de talks.
 */
public class PlanningTalk extends Observable {

    /**
     * Liste des talks regroupés par date de session.
     */
    private List<Talk> talks;

    /**
     * Retourne la liste des talks regroupés par date de session.
     * @return the groupedTalks
     */
    public List<Talk> getGroupedTalks() {
        return talks;
    }

    /**
     * Renseigne la liste des talks regroupés par date de session.
     * @param groupedTalks
     *            the groupedTalks to set
     */
    public void setGroupedTalks(List<Talk> talks) {
        this.talks = talks;
        setChanged();
        notifyObservers(this);
    }

}
