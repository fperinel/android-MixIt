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
    private List<GroupedTalks> groupedTalks;

    /**
     * Retourne la liste des talks regroupés par date de session.
     * 
     * @return the groupedTalks
     */
    public List<GroupedTalks> getGroupedTalks() {
        return groupedTalks;
    }

    /**
     * Renseigne la liste des talks regroupés par date de session.
     * 
     * @param groupedTalks
     *            the groupedTalks to set
     */
    public void setGroupedTalks(List<GroupedTalks> groupedTalks) {
        this.groupedTalks = groupedTalks;
        setChanged();
        notifyObservers(this);
    }

}
