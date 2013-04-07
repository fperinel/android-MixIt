package com.level42.mixit.models;

import java.util.List;
import java.util.Observable;

/**
 * Objet représentant une liste de lightning Talks Permet d'ajouter un observer
 */
public class LightningTalkList extends Observable {

    private List<LightningTalk> talks;

    /**
     * @return the talks
     */
    public List<LightningTalk> getTalks() {
        return talks;
    }

    /**
     * @param talks
     *            the talks to set
     */
    public void setTalks(List<LightningTalk> talks) {
        this.talks = talks;
        setChanged();
        notifyObservers(this);
    }

}
