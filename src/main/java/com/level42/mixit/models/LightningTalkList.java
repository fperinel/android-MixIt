package com.level42.mixit.models;

import java.util.List;
import java.util.Observable;

/**
 * Objet représentant une liste de lightning Talks.
 */
public class LightningTalkList extends Observable {

    /**
     * Liste des lightning talks.
     */
    private List<LightningTalk> talks;

    /**
     * Retourne la liste des lightning talks.
     * 
     * @return the talks
     */
    public List<LightningTalk> getTalks() {
        return talks;
    }

    /**
     * Reseigne la liste des lightning talks.
     * 
     * @param talks
     *            the talks to set
     */
    public void setTalks(List<LightningTalk> talks) {
        this.talks = talks;
        setChanged();
        notifyObservers(this);
    }

}
