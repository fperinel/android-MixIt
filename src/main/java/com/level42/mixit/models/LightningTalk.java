package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un LightningTalk
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LightningTalk extends Talk {

    /**
     * Nombre de vote
     */
    private Integer nbVotes;

    /**
     * Retourne le nombre de vote
     * 
     * @return the nbVotes
     */
    public Integer getNbVotes() {
        return nbVotes;
    }

    /**
     * Renseigne le nombre de vote
     * 
     * @param nbVotes
     *            the nbVotes to set
     */
    public void setNbVotes(Integer nbVotes) {
        this.nbVotes = nbVotes;
    }
}
