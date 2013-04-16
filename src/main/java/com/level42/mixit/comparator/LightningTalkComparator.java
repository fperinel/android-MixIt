package com.level42.mixit.comparator;

import java.util.Comparator;

import com.level42.mixit.models.LightningTalk;

/**
 * Méthode de comparaison pour les LightningTalks
 */
public class LightningTalkComparator implements Comparator<LightningTalk> {

    @Override
    public int compare(LightningTalk arg0, LightningTalk arg1) {
        return arg1.getNbVotes().compareTo(arg0.getNbVotes());
    }
    
}
